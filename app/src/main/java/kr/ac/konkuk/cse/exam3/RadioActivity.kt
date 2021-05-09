package kr.ac.konkuk.cse.exam3

import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

class RadioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)

        init()
    }

    fun init() {
        val radioGroup= findViewById<RadioGroup>(R.id.radioGroup)
        val imageView= findViewById<ImageView>(R.id.imageView)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioButton1 -> {
                        imageView.setImageResource(R.drawable.image_1)
                    }
                    R.id.radioButton2 -> {
                        imageView.setImageResource(R.drawable.image_2)
                    }
                    R.id.radioButton3 -> {
                        imageView.setImageResource(R.drawable.image_3)
                    }
                }
        }
    }
}