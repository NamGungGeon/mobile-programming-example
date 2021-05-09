package kr.ac.konkuk.cse.exam7

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun onClick(view: View) {
        val intent = Intent(this, IntentFilterActivity::class.java)
        startActivity(intent)
    }
}