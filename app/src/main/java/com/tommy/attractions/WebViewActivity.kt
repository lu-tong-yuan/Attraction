package com.tommy.attractions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.tommy.attractions.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        binding.wv.settings.javaScriptEnabled = true
        binding.wv.webViewClient = WebViewClient()
        url?.let {
            binding.wv.loadUrl(it)
        }
    }

    override fun onBackPressed() {
        if (binding.wv.canGoBack()) {
            binding.wv.goBack()
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
}