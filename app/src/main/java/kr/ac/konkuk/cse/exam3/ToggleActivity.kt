package kr.ac.konkuk.cse.exam3

import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

class ToggleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toggle)

        init()
    }

    fun init() {
        val toggleBtn= findViewById<ToggleButton>(R.id.toggleButton)
        toggleBtn.setOnCheckedChangeListener{
            btnView, checked->
                Toast.makeText(this, if(checked) "On" else "Off", Toast.LENGTH_SHORT).show()

        }
    }
}