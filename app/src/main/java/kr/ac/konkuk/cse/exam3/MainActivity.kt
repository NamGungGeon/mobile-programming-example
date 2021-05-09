package kr.ac.konkuk.cse.exam3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        val bmiBtn = findViewById<Button>(R.id.bmibutton)
        val weight = findViewById<EditText>(R.id.weight)
        val height = findViewById<EditText>(R.id.height)

        val imageView = findViewById<ImageView>(R.id.imageView)

        bmiBtn.setOnClickListener {
            val bmi = weight.text.toString().toDouble() /( height.text.toString().toDouble()/100).pow(2.0)

            var resultString = ""
            when {
                bmi >= 35 -> {
                    resultString = "고도 비만"
                    imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
                }
                bmi >= 23 -> {
                    resultString = "과제중"
                    imageView.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
                }
                bmi >= 18.5 -> {
                    resultString = "정상"
                    imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                }
                else -> {
                    resultString = "저체중"
                    imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
                }
            }

            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()
        }
    }
}