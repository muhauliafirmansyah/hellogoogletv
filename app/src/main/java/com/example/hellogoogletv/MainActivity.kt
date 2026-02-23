package com.example.hellogoogletv

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        
        // Configure WebView settings for an optimal TV experience
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            useWideViewPort = true
            loadWithOverviewMode = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        // Force links and redirects to open in the WebView instead of an external browser
        webView.webViewClient = WebViewClient()

        // Ensure the WebView can receive D-Pad key events
        webView.requestFocus()

        // Load the local webpage from the assets folder
        webView.loadUrl("file:///android_asset/index.html")
    }

    // Support going back in web history with the TV remote back button
    // Or allow JavaScript to handle it via a custom evaluateJavascript check
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Evaluate if the JS app handled the back button (returns true if it consumed the event)
        webView.evaluateJavascript("javascript:(function() { if(typeof handleAppBack === 'function') return handleAppBack(); return false; })()") { result ->
            if (result == "true") {
                // Javascript handled the back navigation (e.g., closed a modal)
            } else if (webView.canGoBack()) {
                webView.goBack()
            } else {
                super.onBackPressed()
            }
        }
    }
}
