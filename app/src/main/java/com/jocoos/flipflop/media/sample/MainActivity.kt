package com.jocoos.flipflop.media.sample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var permissions = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission(permissions)

        broadcast.setOnClickListener {
            startActivity(Intent(this, BroadcastActivity::class.java))
        }
        receiver.setOnClickListener {
            startActivity(Intent(this, ReceiverActivity::class.java))
        }
        conferenceStart.setOnClickListener {
            startActivity(Intent(this, ConferencePublisherActivity::class.java))
        }
        conferenceJoin.setOnClickListener {
            startActivity(Intent(this, ConferenceSubscriberActivity::class.java))
        }
    }

    private fun requestPermission(permissions: Array<String>) {
        var mustRequest = false
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                mustRequest = true
                break
            }
        }
        if (mustRequest) {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) { // If request is cancelled, the result arrays are empty.
        if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted
        } else {
            // permission denied
            return
        }
    }
}