package kr.ac.konkuk.cse.exam6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

class IntroActivity : AppCompatActivity() {

    val ADD_VOC_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        init()
    }

    private fun init() {
        val btn1 = findViewById<Button>(R.id.goVocaBtn)
        val btn2 = findViewById<Button>(R.id.goVocaAddBtn)

        btn1.setOnClickListener {
            val intent = Intent(this, WordMasterActivity::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener {
            val intent = Intent(this, AddVocaActivity::class.java)
            startActivityForResult(intent, ADD_VOC_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            ADD_VOC_REQUEST->{
                if(resultCode == Activity.RESULT_OK){
                    val str= data?.getSerializableExtra("voc") as MyWord
                    Toast.makeText(this, str.word+ "단어 추가됨", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}