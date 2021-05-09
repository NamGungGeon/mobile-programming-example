package kr.ac.konkuk.cse.exam6

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.cse.R
import java.util.*
import kotlin.collections.ArrayList

class WordMasterActivity : AppCompatActivity() {
    var data = ArrayList<MyWord>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyWordAdapter
    lateinit var tts: TextToSpeech
    var ttsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_master)

        init()
        initRecylcerView()
        initTTS()
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US
        })
    }

    private fun initRecylcerView() {
        recyclerView = findViewById<RecyclerView>(R.id.wordList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyWordAdapter(data)
        adapter.itemClickListener = object : MyWordAdapter.OnItemClickListener {
            override fun onItemClicked(
                holder: MyWordAdapter.ViewHolder,
                view: View,
                data: MyWord,
                position: Int
            ) {
                if (ttsReady) {
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
                }
                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter
        val simpleCB = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCB)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun readFileScan(scanner: Scanner) {

        while (scanner.hasNextLine()) {
            val word = scanner.nextLine()
            val meaning = scanner.nextLine()
            data.add(MyWord(word, meaning))
        }
        scanner.close()
    }

    private fun init() {
        try {
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "추가된 단어가 없음", Toast.LENGTH_SHORT).show()
        }
        val scan1 = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan1)
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}