package com.android.socialchat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.socialchat.R
import com.android.socialchat.constants.Constants
import com.android.socialchat.databinding.ActivityLoginBinding
import com.android.socialchat.datalocal.AppPrefs
import com.android.socialchat.models.UsersModel
import com.android.socialchat.viewmodel.LoginViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val appPrefs: AppPrefs by inject()
    private val constants: Constants by inject()
    private var token: String? = null
    private val loginViewModel: LoginViewModel by viewModel()
    private var username: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoLogin()
        setupViewModel()
        eventOnclick()
    }

    private fun autoLogin() {
        token = appPrefs.getString(constants.USERINFO)
        if (token != null) {
            Gson().fromJson(token, UsersModel::class.java)
            loginViewModel.autoLogin()
        }
    }

    private fun eventOnclick() {
        binding.btnLogin.setOnClickListener {
            getDataInput()
            if (username != null && password != null) {
                loginViewModel.login(username!!, password!!)
            }
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun getDataInput() {
        username = binding.edtUsername.text.toString()
        password = binding.edtPassword.text.toString()
        if (username == "" || password == "") {
            return Toast.makeText(this, getString(R.string.input_error_text), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setupViewModel() {
        loginViewModel.autoLogin.observe(this@LoginActivity, { message ->
            if (message.error == null) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
        })
        loginViewModel.user.observe(this@LoginActivity, { user ->
            if (user.error == null) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            } else {
                Toast.makeText(this@LoginActivity,
                    getString(R.string.username_password_error_text),
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

}