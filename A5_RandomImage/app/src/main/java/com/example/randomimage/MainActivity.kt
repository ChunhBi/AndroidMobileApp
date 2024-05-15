package com.example.randomimage

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.randomimage.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    var curImageName: String? = "ashe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        curImageName = sharedPreferences.getString("image_name", "ashe")
        binding.imageContent.setImageDrawable(ContextCompat.getDrawable(
            this,
            when (curImageName) {
                "townscaper" -> R.drawable.townscaper
                "ashe" -> R.drawable.ashe
                "azil" -> R.drawable.azil
                "lux" -> R.drawable.lux
                "sivil" -> R.drawable.sivil
                else -> R.drawable.townscaper
            }))
        binding.textInfo.setText(sharedPreferences.getString("image_title", "random image"))

        binding.selectImgButton.setOnClickListener {
            val random = Random()
            val randomNumber = random.nextInt(5)
            val randomImgDrawable = when (randomNumber) {
                0 -> R.drawable.townscaper
                1 -> R.drawable.ashe
                2 -> R.drawable.azil
                3 -> R.drawable.lux
                4 -> R.drawable.sivil
                else -> R.drawable.townscaper
            }
            curImageName = when (randomNumber) {
                0 -> "townscaper"
                1 -> "ashe"
                2 -> "azil"
                3 -> "lux"
                4 -> "sivil"
                else -> "townscaper"
            }
            binding.imageContent.setImageDrawable(ContextCompat.getDrawable(this, randomImgDrawable))
            binding.textInfo.setText(curImageName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor = sharedPreferences.edit()
        editor.putString("image_title", binding.textInfo.text.toString())
        editor.putString("image_name", curImageName)
        editor.apply() // Use commit() for synchronous saving
    }
}