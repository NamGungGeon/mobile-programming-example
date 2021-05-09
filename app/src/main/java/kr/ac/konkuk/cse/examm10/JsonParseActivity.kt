package kr.ac.konkuk.cse.examm10

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.konkuk.cse.databinding.ActivityJsonParseBinding
import kr.ac.konkuk.cse.databinding.ActivityXmlParseBinding
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class JsonParseActivity : AppCompatActivity() {
    lateinit var binding: ActivityJsonParseBinding

    val scope = CoroutineScope(Dispatchers.IO)
    val url = "https://api.icndb.com/jokes/random"
    val adapter = DaumNewsAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJsonParseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            swiper.setOnRefreshListener {
                swiper.isRefreshing = true
                getJSON()
            }
            newsRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this@JsonParseActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            newsRecyclerView.layoutManager =
                LinearLayoutManager(this@JsonParseActivity, LinearLayoutManager.VERTICAL, false)

            adapter.itemClickListener = object : DaumNewsAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: DaumNewsAdapter.ViewHolder,
                    view: View,
                    data: DaumNews,
                    position: Int
                ) {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(adapter.newsList[position].url))
                    startActivity(intent)
                }
            }
            newsRecyclerView.adapter = adapter
        }
        getJSON()
    }

    fun getJSON() {
        adapter.newsList.clear()
        adapter.notifyDataSetChanged()

        scope.launch {
            val doc = Jsoup.connect(url).ignoreContentType(true).get()
            val json= JSONObject(doc.text())
            val joke= json.getJSONObject("value")
            val jokestr= joke.getString("joke")
            Log.i("json joke", jokestr)

            withContext(Dispatchers.Main) {
                binding.swiper.isRefreshing = false
                Toast.makeText(this@JsonParseActivity, jokestr, Toast.LENGTH_LONG).show()
                adapter.notifyDataSetChanged()
            }
        }
    }
}