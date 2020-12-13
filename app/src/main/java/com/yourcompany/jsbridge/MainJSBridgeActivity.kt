package com.yourcompany.jsbridge

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*

private const val TAG = "JSBridgeActivity"

class MainJSBridgeActivity : BaseJSBridgeActivity() {

    var webSettings: WebSettings? = null
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_s_bridge)
        webView = findViewById(R.id.webView)
        initUI()
        setUpListener()
    }


    fun initUI() {
        webSettings = webView.getSettings()
        webSettings!!.javaScriptEnabled = true
        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()
        webSettings!!.builtInZoomControls = false
        webSettings!!.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings!!.domStorageEnabled = true
        webSettings!!.setAppCacheEnabled(false)
        webSettings!!.setGeolocationEnabled(true)
        webSettings!!.setGeolocationDatabasePath(this.filesDir.path)
        webView.loadUrl("file:///android_asset/index.html")
        //        binding.webView.loadUrl(BASE_URL);
    }

    fun setUpListener() {
        webView.addJavascriptInterface(this, "SuperApp")
        webView.webViewClient = SuperAppPWAClient()
        webView.webChromeClient = object : WebChromeClient() {
            //Other methods for your WebChromeClient here, if needed..
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
    }

    inner class SuperAppPWAClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
/*            if ("yourweb.com".equals(Uri.parse(url).getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;*/
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
//            injectJSInterface(view, JS_URL);
            evaluateJavascript(view, "shohozPWA.setSuperAppData", "{\"ads_id\":\"3156a5f7-c727-4f87-b6b5-7f9a527be392\",\"fcm_token\":\"ezlm2aeHQm-dRPMUfW1H9E:APA91bENE5dHzJrutCKJZAAVszIhuaAY4uE-3uiIKJuZ4GaB7-lsoIj1C0uCzdeZrTKWNy3VCymxADPTFPkw4EoE4RhlI4jG9MTQAzRYD_dE1qLGqV2qhKcla_k19GOu--IGagzgP7kq\",\"language\":\"en\",\"platform\":\"android\",\"user_agent\":\"Shohoz User/3.1.1 (Android 11; Pixel 3; google blueline; en)\"}")
        }

        fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
            // Check if the key event was the Back button and if there's history
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
                return true
            }
            // If it wasn't the Back key or there's no web page history, bubble up to the default
            // system behavior (probably exit the activity)
//            return super.onKeyDown(keyCode, event)
            return false
        }
    }

}