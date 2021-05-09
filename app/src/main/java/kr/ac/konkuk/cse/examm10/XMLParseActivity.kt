package kr.ac.konkuk.cse.examm10

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.konkuk.cse.databinding.ActivityXmlParseBinding
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class XMLParseActivity : AppCompatActivity() {
    lateinit var binding: ActivityXmlParseBinding

    val scope = CoroutineScope(Dispatchers.IO)
    val url = "https://fs.jtbc.joins.com//RSS/culture.xml"
    val adapter = DaumNewsAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityXmlParseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            swiper.setOnRefreshListener {
                swiper.isRefreshing = true
                getRssNews()
            }
            newsRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this@XMLParseActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            newsRecyclerView.layoutManager =
                LinearLayoutManager(this@XMLParseActivity, LinearLayoutManager.VERTICAL, false)

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
        getRssNews()
    }

    fun getRssNews() {
        adapter.newsList.clear()
        adapter.notifyDataSetChanged()

        scope.launch {
            val doc = Jsoup.connect(url).parser(
                Parser.xmlParser()
            ).get()
            val headlines = doc.select("item")
            for (news in headlines) {
                adapter.newsList.add(
                    DaumNews(
                        news.select("title").text(),
                        news.select("link").text()
                    )
                )
            }

            withContext(Dispatchers.Main) {
                binding.swiper.isRefreshing = false
                adapter.notifyDataSetChanged()
            }
        }
    }
}