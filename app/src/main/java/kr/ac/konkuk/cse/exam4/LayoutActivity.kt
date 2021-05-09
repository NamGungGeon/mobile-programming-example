package kr.ac.konkuk.cse.exam4

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

open class LayoutActivity : AppCompatActivity() {
    var checkIds = intArrayOf(
        R.id.checkBox1,
        R.id.checkBox2,
        R.id.checkBox3,
        R.id.checkBox4,
        R.id.checkBox5,
        R.id.checkBox6,
        R.id.checkBox7,
        R.id.checkBox8,
        R.id.checkBox9,
        R.id.checkBox10,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        init()
    }

    fun init() {
        for(id in checkIds){
            val checkBox= findViewById<CheckBox>(id)
            checkBox.setOnCheckedChangeListener{
                button, isChecked->
                    val imgId= resources.getIdentifier(button.text.toString(), "id", packageName)
                    val imageView= findViewById<ImageView>(imgId)
                    imageView.visibility= if(isChecked) View.VISIBLE else View.INVISIBLE
            }
        }
    }
}