package com.yourcompany.jsbridge

import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

private const val TAG = "BaseJSBridgeActivity"
abstract class BaseJSBridgeActivity : AppCompatActivity() {
    @get:JavascriptInterface
    val accessToken: String
        get() = "Here Your Object"

    @get:JavascriptInterface
    val iAMAccessToken: String
        get() = "Here Your Object"

    @get:JavascriptInterface
    val accessTokenByRefreshToken: String
        get() = "Here Your Object"

    @get:JavascriptInterface
    val iAMAccessTokenByRefreshToken: String
        get() = "Here Your Object"

    @get:JavascriptInterface
    val currentLocation: String?
        get() = null

    @get:JavascriptInterface
    val language: String
        get() = "Here Your Object"

    @JavascriptInterface
    fun setLanguage(language: String): String {
        return language
    }

    @Synchronized
    protected fun evaluateJavascript(webView: WebView, jsFunctionName: String, o: Any?) {
        val javaScript = "javascript: $jsFunctionName( ${Gson().toJson(o)})"
        Log.i(TAG, javaScript)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(javaScript, null)
        } else {
            webView.loadUrl(javaScript)
        }
    }

    @Synchronized
    protected fun evaluateJavascript(webView: WebView, jsFunctionName: String, s: String) {
        val javaScript = "javascript:$jsFunctionName($s)"
        Log.i(TAG, javaScript)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(javaScript, null)
        } else {
            webView.loadUrl(javaScript)
        }
    }

    @Synchronized
    protected fun injectJSInterface(webView: WebView, JSInterfaceScriptUrl: String) {
        val script = "javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                "script.src = \"" + JSInterfaceScriptUrl + "\";" +
                "parent.appendChild(script)" +
                "})()"
        Log.i(TAG, script)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(script, null)
        } else {
            webView.loadUrl(script)
        }
    }

    fun UserAgentInterceptor(): String {
        return java.lang.String.format(
            Locale.US,
            "%s/%s (Android %s; %s; %s %s; %s)",
            "Your App Name",
            "00.1",
            Build.VERSION.RELEASE,
            Build.MODEL,
            Build.BRAND,
            Build.DEVICE,
            Locale.getDefault().language
        )
    }
}