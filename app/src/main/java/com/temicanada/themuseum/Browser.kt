package com.temicanada.themuseum

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.robotemi.sdk.NlpResult
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import org.jetbrains.annotations.NotNull

class Browser : AppCompatActivity(), OnRobotReadyListener, Robot.NlpListener {

    val ACTION_OPEN_MUSEUM = "open.museum"
    val ACTION_OPEN_EVENT = "open.event"
    val ACTION_OPEN_EXHIBITION = "open.exhibition"
    val ACTION_OPEN_GALLERY = "open.gallery"
    val ACTION_OPEN_BECOME_MEMBER = "open.become.member"
    val ACTION_OPEN_MUSEUM_PLUS = "open.museum.plus"
    lateinit var webBrowser: WebView

    override fun onStart() {
        super.onStart()
        Robot.getInstance().addOnRobotReadyListener(this)
        Robot.getInstance().addNlpListener(this)
    }

    override fun onStop() {
        super.onStop()
        Robot.getInstance().removeOnRobotReadyListener(this)
        Robot.getInstance().removeNlpListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browser)

        webBrowser = findViewById(R.id.webView)
        var back: Button = findViewById(R.id.button)

        webBrowser.settings.setJavaScriptEnabled(true);
        webBrowser.settings.useWideViewPort = true;
        webBrowser.settings.loadWithOverviewMode = true;
        webBrowser.settings.setSupportZoom(true);
        webBrowser.settings.setSupportMultipleWindows(true);
        webBrowser.settings.domStorageEnabled = true;
        webBrowser.settings.allowContentAccess = true;
        webBrowser.settings.allowFileAccess = true;
        webBrowser.settings.allowFileAccessFromFileURLs = true;
        webBrowser.settings.javaScriptCanOpenWindowsAutomatically = true;
        webBrowser.settings.loadsImagesAutomatically = true;
        webBrowser.settings.setAppCacheEnabled(true);
        webBrowser.settings.setGeolocationEnabled(true);
        webBrowser.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        webBrowser.setBackgroundColor(Color.WHITE);

        val UrlString = intent.getStringExtra("URL")

        if (UrlString.isNotEmpty()) {
            webBrowser.loadUrl(UrlString)
        }

    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            try {
                val activityInfo =
                    packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
                Robot.getInstance().onStart(activityInfo)
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }
            Robot.getInstance().hideTopBar()
        }
    }

    override fun onNlpCompleted(nlpResult: NlpResult) {
        var result = nlpResult.action
        val intentBrowser = Intent(applicationContext, Browser::class.java)
        when (result) {
            ACTION_OPEN_MUSEUM -> intent = Intent(this, MainActivity::class.java)
            ACTION_OPEN_EVENT -> webBrowser.loadUrl("https://themuseum.ca/events/")
            ACTION_OPEN_EXHIBITION -> webBrowser.loadUrl("https://themuseum.ca/exhibitions/")
            ACTION_OPEN_BECOME_MEMBER -> webBrowser.loadUrl("https://themuseum.ca/membership/")
            ACTION_OPEN_MUSEUM_PLUS -> webBrowser.loadUrl("https://themuseum.ca/themuseumplus/")

        }
    }

    fun goBack(view: View) {
        finish()
        startActivity(Intent(this,MainActivity::class.java))

    }


}