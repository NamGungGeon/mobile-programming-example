package kr.ac.konkuk.cse.examm10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import kr.ac.konkuk.cse.databinding.ActivityAsyncBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class AsyncActivity : AppCompatActivity() {
    lateinit var binding: ActivityAsyncBinding
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAsyncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            requestBtn.setOnClickListener {
                scope.launch {
                    var data = ""
                    connectProgressBar.visibility = View.VISIBLE

                    withContext(Dispatchers.IO) {
                        data = loadNetworkSomething(URL(urlEditText.text.toString()))
                    }
//                    CoroutineScope(Dispatchers.IO).async {
//                        data = loadNetworkSomething(URL(urlEditText.text.toString()))
//                    }.await()

                    responseText.text = data
                    connectProgressBar.visibility = View.GONE
                }
            }
        }
    }

    fun loadNetworkSomething(url: URL): String {
        var result = ""

        val connect = url.openConnection() as HttpURLConnection
        connect.connectTimeout = 4000
        connect.readTimeout = 4000
        connect.requestMethod = "GET"
        connect.connect()

        val responseCode = connect.responseCode
        if (responseCode == 200) {
            result = streamToString(connect.inputStream)
        }

        return result
    }

    fun streamToString(inputStream: InputStream): String {
        val bReader = BufferedReader(InputStreamReader(inputStream))
        var result = ""

        try {
            var line: String
            do {
                line = bReader.readLine()
                if (line != null) {
                    result += line
                    Log.i("readLine", line)
                }
            } while (line != null)
            bReader.close()
        } catch (e: Exception) {
            Log.e("error", "읽기 실패")
            e.printStackTrace()
        }
        return result
    }

}