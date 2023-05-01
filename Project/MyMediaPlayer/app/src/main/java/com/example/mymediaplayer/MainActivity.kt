package com.example.mymediaplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mymediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    companion object {
        const val TAG = "MainActivity"
    }

    private var mService: Messenger? = null
    private lateinit var mBoundServiceIntent: Intent
    private var mServiceBound = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            mServiceBound = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            if (mServiceBound) {
                try {
                    mService?.send(Message.obtain(null, MediaService.PLAY, 0, 0))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.btnStop.setOnClickListener {

            if (mServiceBound) {
                try {
                    mService?.send(Message.obtain(null, MediaService.STOP, 0, 0))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        mBoundServiceIntent = Intent(this, MediaService::class.java)
        mBoundServiceIntent.action = MediaService.ACTION_CREATE

        startService(mBoundServiceIntent)
        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        unbindService(mServiceConnection)
        mBoundServiceIntent.action = MediaService.ACTION_DESTROY

        startService(mBoundServiceIntent)
    }
}