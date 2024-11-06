package com.doterra.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.core.content.ContextCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val confirmPasswordField = findViewById<EditText>(R.id.confirmPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            if (password == confirmPassword) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                                if (verifyTask.isSuccessful) {
                                    val userData = hashMapOf(
                                        "email" to email,
                                        "uid" to user.uid
                                    )
                                    db.collection("users").document(user.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Registration successful. Please check your email for verification.", Toast.LENGTH_LONG).show()
                                            val intent = Intent(this, LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            // Handle failure
                                            Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_LONG).show()
                                        }
                                } else {
                                    // Failed to send verification email
                                    Toast.makeText(this, "Failed to send verification email: ${verifyTask.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            // Registration failed
                            task.exception?.message?.let {
                                // Show error message
                                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            } else {
                // Passwords do not match
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            }
        }
    }
}
