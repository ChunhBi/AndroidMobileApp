package com.example.simplemath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val calcButton: Button = findViewById(R.id.calc_button)
        val firstText: EditText = findViewById(R.id.firstNumber)
        val secondText: EditText = findViewById(R.id.secondNumber)
        val output: TextView = findViewById(R.id.output)
        var radioButton: RadioButton = findViewById(R.id.add_button)
        radioButton.isChecked = true

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioButton = findViewById(checkedId)
            // disable other buttons
            for (i in 0 until group.childCount) {
                val curButton = group.getChildAt(i) as RadioButton
                curButton.isChecked = curButton.id == checkedId
            }
        }

        calcButton.setOnClickListener { view: View ->
            val selectedOption = radioButton.text.toString()
            try{
                val first_num = firstText.text.toString().toFloat()
                val second_num = secondText.text.toString().toFloat()
                if (second_num == 0f) {
                    if (selectedOption == "/")
                        Snackbar.make(output, "You can't divide by 0!", Snackbar.LENGTH_SHORT).show()
                    else if (selectedOption == "mod")
                        Snackbar.make(output, "You can't modulo by 0!", Snackbar.LENGTH_SHORT).show()
                }
                val result = when (selectedOption) {
                    "+" -> (first_num + second_num).toString()
                    "-" -> (first_num - second_num).toString()
                    "*" -> (first_num * second_num).toString()
                    "/" -> (first_num / second_num).toString()
                    "mod" -> (first_num % second_num).toString()
                    else -> "NAN"
                }
                output.text = result
            }
            catch (e: NumberFormatException) {
                Snackbar.make(output, "Please enter numbers!", Snackbar.LENGTH_SHORT).show()

            }
        }
    }
}