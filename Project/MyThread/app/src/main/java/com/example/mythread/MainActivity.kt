package com.example.mythread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.example.mythread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())

        binding.btnStart.setOnClickListener {
            lifecycleScope.launch (Dispatchers.Default){
                try {
                    for (i in 1..10) {
                        Thread.sleep(500)
                        val present = i * 10
                        withContext(Dispatchers.Main) {
                            if (present == 100) {
                                binding.tvStatus.setText(R.string.task_completed)
                            } else {
                                binding.tvStatus.text =
                                    String.format(getString(R.string.compressing), present)
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}