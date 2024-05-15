package com.example.part3temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import com.example.part3temperatureconverter.databinding.ActivityMainBinding

const val MAX_CELCIUS = 100f
const val MIN_CELCIUS = 0f
const val MAX_FAHRENHEIT = 212f
const val MIN_FAHRENHEIT = 0f
const val COLD_CELCIUS = 20
const val HOT_CELCIUS = 80

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.celciusBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar == null) return
                val progress = seekBar.progress
                val value:Float = (MAX_CELCIUS-MIN_CELCIUS)*progress/100f
                binding.celciusValue.setText(value.toString())
                binding.fahrenheitValue.setText(celciusToFahrenheit(value).toString())
                binding.fahrenheitBar.setProgress((celciusToFahrenheit(value) * 100f / (MAX_FAHRENHEIT- MIN_FAHRENHEIT)).toInt())
                reactWithCelcius(value)
            }
        })
        binding.fahrenheitBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar == null) return
                val progress = seekBar.progress
                var value:Float = (MAX_FAHRENHEIT- MIN_FAHRENHEIT)*progress/100f
                val minFahrenheit = celciusToFahrenheit(MIN_CELCIUS)
                if (value < minFahrenheit) {
                    value = minFahrenheit
                    binding.fahrenheitBar.setProgress((minFahrenheit * 100f / (MAX_FAHRENHEIT- MIN_FAHRENHEIT)).toInt())
                }
                binding.celciusValue.setText(fahrenheitToCelcius(value).toString())
                binding.fahrenheitValue.setText(value.toString())
                binding.celciusBar.setProgress((fahrenheitToCelcius(value) * 100f / (MAX_CELCIUS- MIN_CELCIUS)).toInt())
                reactWithCelcius(fahrenheitToCelcius(value))
            }
        })
    }
    private fun reactWithCelcius(value:Float) {
        if (value <= COLD_CELCIUS) {
            binding.interestingMsg.setText("I wish it were warmer")
        }
        else if (value >= HOT_CELCIUS) {
            binding.interestingMsg.setText("I wish it were colder")
        }
        else{
            binding.interestingMsg.setText("Pleasant temperature")
        }
    }
    private fun celciusToFahrenheit(value:Float): Float {
        return value*1.8f+32
    }
    private fun fahrenheitToCelcius(value:Float): Float {
        return (value-32)/1.8f
    }
}