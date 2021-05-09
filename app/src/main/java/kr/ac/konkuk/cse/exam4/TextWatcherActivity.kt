package kr.ac.konkuk.cse.exam4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kr.ac.konkuk.cse.R

open class TextWatcherActivity : AppCompatActivity() {

    val countries = mutableListOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "American Samoa",
        "Andorra",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium"
    )
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textwatcher)

        init()
    }

    fun init() {
        val autoCompleteView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val countries2= resources.getStringArray(R.array.countries_array)
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        autoCompleteView.setAdapter(adapter)

        autoCompleteView.setOnItemClickListener{
            parent, view, position, id->
                val item= parent.getItemAtPosition(position).toString()
                Toast.makeText(this, "선택된 항목: $item", Toast.LENGTH_SHORT).show()
        }

        val mAutoCompleteView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        mAutoCompleteView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        mAutoCompleteView.setAdapter(adapter)

        val button= findViewById<Button>(R.id.button)
        val editText= findViewById<EditText>(R.id.editText)

        editText.addTextChangedListener {
            val str= it.toString()
            button.isEnabled= str.isNotEmpty()
        }
//        editText.addTextChangedListener(
//            afterTextChanged= {
//                val str= it.toString()
//                button.isEnabled= str.isNotEmpty()
//            }
//        )
//        editText.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                //TODO("Not yet implemented")
//                val str= s.toString()
//                button.isEnabled= str.isNotEmpty()
//            }
//        })
        button.setOnClickListener{
            adapter.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }

    }
}