package kr.ac.konkuk.cse.examm10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kr.ac.konkuk.cse.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding:ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            webview.webViewClient= WebViewClient()
            webview.settings.javaScriptEnabled= true
            webview.settings.builtInZoomControls= true
            webview.settings.defaultTextEncodingName= "utf-8"

            //캐시 모드 변경
            webview.settings.cacheMode= WebSettings.LOAD_CACHE_ELSE_NETWORK

            webview.loadUrl("https://www.google.com")
        }
    }
}