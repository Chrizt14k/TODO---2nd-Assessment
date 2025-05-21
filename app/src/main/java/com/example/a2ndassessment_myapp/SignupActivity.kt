package com.example.a2ndassessment_myapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Basic input validation
            when {
                username.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                }

                username.length < 4 -> {
                    Toast.makeText(this, "Username must be at least 4 characters.", Toast.LENGTH_SHORT).show()
                }

                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
                }

                !password.matches(".*[0-9!@#$%^&*()_+=-].*".toRegex()) -> {
                    Toast.makeText(this, "Password should include at least one number or symbol.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    // Save credentials
                    val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("username", username)
                        putString("password", password)
                        apply()
                    }

                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

                }
            }
        }
    }
}
