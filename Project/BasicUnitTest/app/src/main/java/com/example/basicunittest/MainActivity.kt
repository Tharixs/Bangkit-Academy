package com.example.basicunittest

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.basicunittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel(CuboidModel())

        binding.btnSave.setOnClickListener(this)
        binding.btnLuas.setOnClickListener(this)
        binding.btnVolume.setOnClickListener(this)
        binding.btnKeliling.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        val length = binding.edLength.text.toString().trim()
        val width = binding.edWidth.text.toString().trim()
        val height = binding.edHeight.text.toString().trim()

        when {
            TextUtils.isEmpty(length) -> binding.edLength.error = "Field ini tidak boleh kosong"
            TextUtils.isEmpty(width) -> binding.edWidth.error = "Field ini tidak boleh kosong"
            TextUtils.isEmpty(height) -> binding.edHeight.error = "Field ini tidak boleh kosong"
            else -> {
                val l = length.toDouble()
                val w = width.toDouble()
                val h = height.toDouble()

                when (v?.id) {
                    R.id.btnSave -> {
                        mainViewModel.save(l, w, h)
                        visible()
                    }
                    R.id.btnLuas -> {
                        binding.result.text = mainViewModel.getSurfaceArea().toString()
                        gone()
                    }
                    R.id.btnVolume -> {
                        binding.result.text = mainViewModel.getVolume().toString()
                        gone()
                    }
                    R.id.btnKeliling -> {
                        binding.result.text = mainViewModel.getCircumference().toString()
                        gone()
                    }
                }
            }
        }

    }

    private fun gone() {
        binding.btnVolume.visibility = View.GONE
        binding.btnLuas.visibility = View.GONE
        binding.btnKeliling.visibility = View.GONE
        binding.btnSave.visibility = View.VISIBLE
    }

    private fun visible() {
        binding.btnVolume.visibility = View.VISIBLE
        binding.btnLuas.visibility = View.VISIBLE
        binding.btnKeliling.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
    }
}