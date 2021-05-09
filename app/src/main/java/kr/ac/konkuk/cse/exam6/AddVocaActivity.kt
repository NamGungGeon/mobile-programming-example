package kr.ac.konkuk.cse.exam6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R
import java.io.PrintStream

class AddVocaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voca)
        init()
    }

    private fun init() {
        val addBtn = findViewById<Button>(R.id.button3)
        val cancelBtn = findViewById<Button>(R.id.button4)
        val editWord = findViewById<EditText>(R.id.wordText)
        val editMeaning = findViewById<EditText>(R.id.meaningText)

        addBtn.setOnClickListener {
            writeFile(editWord.text.toString(), editMeaning.text.toString())
        }
        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()

        val intent = Intent()
        intent.putExtra("voc", MyWord(word, meaning))
        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}