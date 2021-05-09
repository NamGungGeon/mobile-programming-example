package kr.ac.konkuk.cse.examm10

import android.content.Intent
import android.content.Intent.ACTION_VIEW
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
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityWebParseBinding
import org.jsoup.Jsoup

class WebParseActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebParseBinding
    val scope = CoroutineScope(Dispatchers.IO)
    val url = "https://www.daum.net"
    val adapter = DaumNewsAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebParseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            swiper.setOnRefreshListener {
                swiper.isRefreshing = true
                getNews()
            }
            newsRecyclerView.addItemDecoration(DividerItemDecoration(this@WebParseActivity, LinearLayoutManager.VERTICAL))
            newsRecyclerView.layoutManager =
                LinearLayoutManager(this@WebParseActivity, LinearLayoutManager.VERTICAL, false)

            adapter.itemClickListener = object : DaumNewsAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: DaumNewsAdapter.ViewHolder,
                    view: View,
                    data: DaumNews,
                    position: Int
                ) {
                    val intent = Intent(ACTION_VIEW, Uri.parse(adapter.newsList[position].url))
                    startActivity(intent)
                }
            }
            newsRecyclerView.adapter = adapter
        }
        getNews()
    }

    fun getNews() {
        adapter.newsList.clear()
        scope.launch {
            val doc = Jsoup.connect(url).get()
            val headlines = doc.select("ul.list_txt>li>a")
            for (news in headlines) {
                adapter.newsList.add(DaumNews(news.text(), news.absUrl("href")))
            }

            withContext(Dispatchers.Main) {
                binding.swiper.isRefreshing = false
                adapter.notifyDataSetChanged()
            }
        }
    }
}