package com.bignerdranch.android.simpleboggle

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var controlPanelFragment: controlPanel
    private lateinit var boardFragment: board
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controlPanelFragment = supportFragmentManager.findFragmentById(R.id.control_fragment) as controlPanel
        boardFragment = supportFragmentManager.findFragmentById(R.id.board_fragment) as board
        boardFragment.setListener(controlPanelFragment)
        controlPanelFragment.setListener(boardFragment)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometerSensor != null) {
            val sensorListener = AccelerometerListener(this)
            sensorManager.registerListener(sensorListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        else {
            Log.d("shake_detect","Device not available!")
        }
    }
    fun newGame() {
        boardFragment.resetBoard()
    }
}

interface BoardListener {
    fun resetBoard()
}
interface ControlListener {
    fun setScore(score: Int)
    fun getScore(): Int
}

class AccelerometerListener(val mainActivity: MainActivity
): SensorEventListener {
    private var lastMagnitude = 0.0
    private val SHAKE_THRESHOLD = 3
    private val SHAKE_COOLDOWN = 1000L // Time delay in milliseconds
    private var lastShakeTime: Long = 0

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastShakeTime < SHAKE_COOLDOWN)
                    return
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
//                Log.d("shake_detect",x.toString() + " " + y.toString() + " "+ z.toString())

                val currentMagnitude = Math.sqrt((x * x + y * y + z * z).toDouble())
                val delta = currentMagnitude - lastMagnitude
                lastMagnitude = currentMagnitude

//                Log.d("shake_detect",delta.toString())
                if (delta > SHAKE_THRESHOLD || delta < -SHAKE_THRESHOLD) {
                    // Shake detected!
//                    Log.d("shake_detect","Shake detected2!")
                    mainActivity.newGame()
                    lastShakeTime = System.currentTimeMillis()
                }
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}

