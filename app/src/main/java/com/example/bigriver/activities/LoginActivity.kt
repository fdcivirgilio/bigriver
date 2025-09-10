package com.example.bigriver.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bigriver.MainActivity
import com.example.bigriver.R
import com.example.bigriver.ui.viewmodel.UserViewModel
import kotlin.getValue
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener { button ->

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener{ button ->
            val etEmail = findViewById<TextView>(R.id.etEmail)
            val etPassword = findViewById<TextView>(R.id.etPassword)
            if(etPassword.text.toString().isNotEmpty() && etEmail.text.toString().isNotEmpty()){
                userViewModel.getUserByCredentials(emailAddress = etEmail.text.toString(), password = etPassword.text.toString())
                userViewModel.currentUser.observe(this) { user ->
                    if (user != null) {
                        Log.d("LoginActivity", "user id: ${user.id}")
                        val newToken = java.util.UUID.randomUUID().toString()
                        userViewModel.updateUserToken(
                            userId = user.id,
                            path = newToken
                        )
                        val prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                        prefs.edit() {
                            putString("user_token", newToken)
                        }

                        val intent = Intent(this, MainActivity::class.java)
                         startActivity(intent)
                    } else {
                        Log.d("LoginActivity", "Invalid credentials")
                    }
                }
            }
        }
    }
}