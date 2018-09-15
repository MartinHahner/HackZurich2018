package com.hackzurich2018.mealsh

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<View>(R.id.btnSignIn).setOnClickListener {
            val email = findViewById<EditText>(R.id.etMail).text.toString()
            val id = email.hashCode()
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("User-ID", id)
            intent.putExtra("Email", email)
            finish()
            startActivity(intent)
        }
    }
}
