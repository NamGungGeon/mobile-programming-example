package kr.ac.konkuk.cse.exam3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.konkuk.cse.R

class AutoCompleteActivity : AppCompatActivity() {

    val countries = arrayOf(
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
        setContentView(R.layout.activity_autocomplete)

        init()
    }

    fun init() {
        val autoCompleteView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val countries2= resources.getStringArray(R.array.countries_array)
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries2)
        autoCompleteView.setAdapter(adapter)

        autoCompleteView.setOnItemClickListener{
            parent, view, position, id->
                val item= parent.getItemAtPosition(position).toString()
                Toast.makeText(this, "선택된 항목: $item", Toast.LENGTH_SHORT).show()
        }

        val mAutoCompleteView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        mAutoCompleteView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        mAutoCompleteView.setAdapter(adapter)

    }
}