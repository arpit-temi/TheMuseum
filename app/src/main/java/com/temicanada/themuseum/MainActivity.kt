package com.temicanada.themuseum

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.robotemi.sdk.NlpResult
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.listeners.OnRobotReadyListener
import org.jetbrains.annotations.NotNull


class MainActivity : AppCompatActivity(), OnRobotReadyListener, Robot.NlpListener, Robot.TtsListener {

    val ACTION_OPEN_MUSEUM = "open.museum"
    val ACTION_OPEN_EVENT = "open.event"
    val ACTION_OPEN_EXHIBITION = "open.exhibition"
    val ACTION_OPEN_GALLERY = "open.gallery"
    val ACTION_OPEN_BECOME_MEMBER = "open.become.member"
    val ACTION_OPEN_MUSEUM_PLUS = "open.museum.plus"

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

    override fun onPause() {
        super.onPause()
        Robot.getInstance().addNlpListener(this)
    }

    override fun onResume() {
        super.onResume()
        Robot.getInstance().addNlpListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Event = findViewById<CardView>(R.id.event_cardview)
        val Exhibition = findViewById<CardView>(R.id.exhibition_cardview)
        val  MusemuPlus = findViewById<CardView>(R.id.museum_plus_cardview)
        val  BecomeMember = findViewById<CardView>(R.id.become_member_cardview)

        val intentBrowser = Intent(applicationContext, Browser::class.java)
        Event.setOnClickListener{
            intentBrowser.putExtra("URL", "https://themuseum.ca/events/")
            startActivity(intent)
        }

        Exhibition.setOnClickListener{
            intentBrowser.putExtra("URL", "https://themuseum.ca/events/")
            startActivity(intent)
        }

        MusemuPlus.setOnClickListener{
            intentBrowser.putExtra("URL", "https://themuseum.ca/events/")
            startActivity(intent)
        }

        BecomeMember.setOnClickListener{
            intentBrowser.putExtra("URL", "https://themuseum.ca/events/")
            startActivity(intent)
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

            Robot.getInstance().speak(TtsRequest.create("Hi,. My Name is Temi. I am your tour guide today.  Let me know if you have any questions ", false))

        }
    }

    override fun onNlpCompleted(@NotNull nlpResult: NlpResult) {
        var result = nlpResult.action
        when (result) {
            ACTION_OPEN_MUSEUM -> intent = Intent(baseContext, MainActivity::class.java)
            ACTION_OPEN_EVENT -> {
                Intent(this, Browser::class.java).apply {
                    putExtra("URL", "https://themuseum.ca/events/")
                }
                startActivity(intent)
            }
            ACTION_OPEN_EXHIBITION -> {
                Intent(this, Browser::class.java).apply {
                    putExtra("URL", "https://themuseum.ca/exhibitions/")
                }
                startActivity(intent)
            }
            ACTION_OPEN_BECOME_MEMBER -> {
                Intent(this, Browser::class.java).apply {
                    putExtra("URL", "https://themuseum.ca/membership/")
                }
                startActivity(intent)
            }
            ACTION_OPEN_MUSEUM_PLUS -> {
                Intent(this, Browser::class.java).apply {
                    putExtra("URL", "https://themuseum.ca/themuseumplus/")
                }
                startActivity(intent)
            }
        }
    }

    fun EventClick(view: View) {

        val intent = Intent(this, Browser::class.java).apply {
            putExtra("URL", "https://themuseum.ca/events/")
        }
        startActivity(intent)

//        val intentBrowser = Intent(applicationContext, Browser::class.java)
//        intentBrowser.putExtra("URL", "https://themuseum.ca/events/")
//        startActivity(intent)
    }

    fun ExhibitionClick(view: View) {
        val intent = Intent(this, Browser::class.java).apply {
            putExtra("URL", "https://themuseum.ca/exhibitions/")
        }
        startActivity(intent)
    }
    fun MembershipClick(view: View) {
        val intent = Intent(this, Browser::class.java).apply {
            putExtra("URL", "https://themuseum.ca/membership/")
        }
        startActivity(intent)
    }
    fun MuseumplusClick(view: View) {
        val intent = Intent(this, Browser::class.java).apply {
            putExtra("URL", "https://themuseum.ca/themuseumplus/")
        }
        startActivity(intent)
    }

    override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
    }
}