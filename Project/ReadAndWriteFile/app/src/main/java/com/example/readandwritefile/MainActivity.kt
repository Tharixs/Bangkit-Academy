package com.example.readandwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.readandwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.buttonNew.id -> newFile()
            binding.buttonOpen.id -> showList()
            binding.buttonSave.id -> saveData()
        }
    }

    private fun saveData() {
        when {
            binding.editTitle.text.toString().isEmpty() ->
                Toast.makeText(this, "File name can't be empty", Toast.LENGTH_SHORT).show()
            binding.editText.text.toString().isEmpty() ->
                Toast.makeText(this, "File can't be empty", Toast.LENGTH_SHORT).show()

            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editText.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.witeToFile(fileModel, this)
                Toast.makeText(this, "File saved ${fileModel.fileName} file", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showList() {
        val fileNames = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose file")
        builder.setItems(fileNames) { _, item ->
            loadData(fileNames[item].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editText.setText("")
        Toast.makeText(this, "New file created", Toast.LENGTH_SHORT).show()
    }


    private fun loadData(s: String) {
        val fileModel = FileHelper.readFromFile(s, this)
        binding.editTitle.setText(fileModel.fileName)
        binding.editText.setText(fileModel.data)
        Toast.makeText(this, "Loading ${fileModel.fileName} data", Toast.LENGTH_SHORT).show()
    }
}