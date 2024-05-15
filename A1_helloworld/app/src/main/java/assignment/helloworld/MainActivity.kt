package assignment.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var hello_button: Button
    private lateinit var hello_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hello_button = findViewById(R.id.hello_button)
        hello_text = findViewById(R.id.hello_text)

        hello_button.setOnClickListener({
            hello_text.setText("Hello World!!")
        })
    }
}