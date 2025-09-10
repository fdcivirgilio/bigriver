package com.example.bigriver.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bigriver.R

class LoginActivity : AppCompatActivity() {
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

            }
        }
    }
}