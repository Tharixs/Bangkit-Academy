package com.example.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.model.TokenManager
import com.example.storyapp.view.response.LoginResponse
import com.example.storyapp.view.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE))
        setupView()
        playAnimation()
        binding.emailEditText.setErrorMessage("Email tidak sesuai")
        binding.passwordEditText.setErrorMessage("Password tidak sesuai")

        setButtonDisable()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginUser(email, password)
        }

        binding.registerTextView.setOnClickListener {
            val intent = android.content.Intent(
                this@LoginActivity,
                com.example.storyapp.view.register.RegisterActivity::class.java
            )
            startActivity(intent)
        }

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonDisable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


    }

    private fun loginUser(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.error == false) {
//                        get token
                        val token = loginResponse.loginResult?.token
                        tokenManager.saveToken(token.toString())
                        val intent = android.content.Intent(
                            this@LoginActivity,
                            com.example.storyapp.view.main.MainActivity::class.java
                        )
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupView() {
        @Suppress("DEPRECATION") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val msgTv = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTv = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEd =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passTv =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val acount = ObjectAnimator.ofFloat(binding.notHaveAcount, View.ALPHA, 1f).setDuration(500)
        val linkRegis =
            ObjectAnimator.ofFloat(binding.registerTextView, View.ALPHA, 1f).setDuration(500)

        val passEd =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val btn = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                msgTv,
                emailTv,
                emailEd,
                passTv,
                passEd,
                btn,
                acount,
                linkRegis
            )
            start()
        }
    }

    private fun setButtonDisable() {
        val password = binding.passwordEditText.text
        binding.loginButton.isEnabled = !(password!!.isEmpty() || password.length < 8)
    }
}