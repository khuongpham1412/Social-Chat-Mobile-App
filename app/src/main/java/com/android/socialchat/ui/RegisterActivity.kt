package com.android.socialchat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.socialchat.R
import com.android.socialchat.databinding.ActivityRegisterBinding
import com.android.socialchat.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var username: String? = null
    private var password: String? = null
    private var name: String? = null
    private var address: String? = null
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        eventOnclick()
    }

    private fun eventOnclick() {
        binding.btnRegister.setOnClickListener {
            getDataInput()
            if (username != null && password != null && name != null && address != null) {
                registerViewModel.register(username!!, password!!, name!!, address!!)
            }
        }
    }

    private fun getDataInput() {
        username = binding.etUsername.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        name = binding.etName.text.toString().trim()
        address = binding.etAddress.text.toString().trim()
        if (username == "" || password == "" || name == "" || address == "") {
            Toast.makeText(this, getString(R.string.input_error_text), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupViewModel() {
        registerViewModel.register.observe(this@RegisterActivity, { register ->
            if (register.error == null) {
                intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@RegisterActivity, register.error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}