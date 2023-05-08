package com.example.storyapp.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.view.retrofit.ApiConfig

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
//                when format email tidak sesuai
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailEditTextLayout.error = "Email tidak sesuai"
                }
//                when format password tidak sesuai
                password.length < 8 -> {
                    binding.passwordEditTextLayout.error = "Password kurang dari 8 karakter"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                else -> {
                    registerUser(name, email, password)
                }
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        val apiConfig = ApiConfig.getApiService()
        apiConfig.register(name, email, password).enqueue(object :
            retrofit2.Callback<com.example.storyapp.view.response.RegisterResponse> {
            override fun onResponse(
                call: retrofit2.Call<com.example.storyapp.view.response.RegisterResponse>,
                response: retrofit2.Response<com.example.storyapp.view.response.RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.error == false) {
                        val intent = android.content.Intent(
                            this@RegisterActivity,
                            com.example.storyapp.view.login.LoginActivity::class.java
                        )
                        Toast.makeText(
                            this@RegisterActivity,
                            "Register Berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(
                call: retrofit2.Call<com.example.storyapp.view.response.RegisterResponse>,
                t: Throwable
            ) {
                android.widget.Toast.makeText(
                    this@RegisterActivity,
                    "Error: ${t.message}",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }

        })
    }

}
