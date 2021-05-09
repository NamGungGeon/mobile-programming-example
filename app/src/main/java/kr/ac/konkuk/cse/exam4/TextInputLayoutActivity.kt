package kr.ac.konkuk.cse.exam4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.ac.konkuk.cse.R

open class TextInputLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textinputlayout)

        init()
    }

    fun init() {
        val tiLayout = findViewById<TextInputLayout>(R.id.textInputLayout1)
        val emailInput = findViewById<TextInputEditText>(R.id.emailText)
        emailInput.addTextChangedListener {
            if (it.toString().contains('@'))
                tiLayout.error = null
            else
                tiLayout.error = "이메일 형식이 올바르지 않습니다"
        }


    }
}