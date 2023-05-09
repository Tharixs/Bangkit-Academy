package com.example.storyapp.view.main

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.storyapp.databinding.ActivityAddStoryBinding
import com.example.storyapp.view.camera.createTempFile
import com.example.storyapp.view.camera.uriToFile
import com.example.storyapp.view.camera.reduceFileImage
import com.example.storyapp.view.model.TokenManager
import com.example.storyapp.view.response.AddResponse
import com.example.storyapp.view.retrofit.ApiConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null
    private lateinit var tokenManager: TokenManager

    companion object {
        const val CAMERAX_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permission not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = Companion.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE))

        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage(tokenManager.getToken().toString()) }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                Companion.REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.example.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddStoryActivity)
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    private fun uploadImage(token: String) {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val deskText = binding.edDeskText.text.toString()
            val description = deskText.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val apiService = ApiConfig.getApiService()
            val uploadImageRequest = apiService.uploadImage(
                "Bearer $token", imageMultipart, description
            )
            uploadImageRequest.enqueue(object : Callback<AddResponse> {
                override fun onResponse(
                    call: Call<AddResponse>,
                    response: Response<AddResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            Toast.makeText(
                                this@AddStoryActivity,
                                responseBody.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@AddStoryActivity, MainActivity::class.java))
                        }
                    } else {
                        Toast.makeText(
                            this@AddStoryActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    Toast.makeText(this@AddStoryActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
        } else {
            Toast.makeText(
                this@AddStoryActivity,
                "Masukkan gambar terlebih dahulu",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }


}