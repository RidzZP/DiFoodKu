package com.example.testinglks4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.testinglks4.Model.ModelUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var txtUsername: EditText
private lateinit var txtPassword: EditText
private lateinit var btnLogin: Button
private  lateinit var btnRegisterActivity: Button
private lateinit var api: Api

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsername = findViewById(R.id.etUsernameLogin)
        txtPassword = findViewById(R.id.etPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegisterActivity = findViewById(R.id.btnRegisterActivity)

        btnRegisterActivity.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set up Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.23/API_Mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Get instance of API interface
        api = retrofit.create(Api::class.java)

        btnLogin.setOnClickListener {
            // Get user input
            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            // Check if input is valid
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send POST request to server to authenticate user
            api.loginUser(username, password).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // Authentication successful, show success message
                        Toast.makeText(this@LoginActivity, "Login Sukses", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Authentication failed, show error message
                        Toast.makeText(this@LoginActivity, "Login Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Authentication failed due to network error, show error message
                    Toast.makeText(this@LoginActivity, "Login Error: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}