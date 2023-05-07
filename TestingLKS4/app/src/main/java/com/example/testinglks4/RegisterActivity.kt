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

private lateinit var txtNamaLengkap: EditText
private lateinit var txtusername: EditText
private lateinit var txtAlamat: EditText
private lateinit var txtPassword: EditText
private lateinit var txtKonfirmasiPassword: EditText
private lateinit var btnDaftar: Button
private lateinit var btnLoginActivity: Button
private lateinit var api: Api

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNamaLengkap = findViewById(R.id.etNamaLengkap)
        txtusername = findViewById(R.id.etUsername)
        txtAlamat = findViewById(R.id.etAlamat)
        txtPassword = findViewById(R.id.etPassword)
        txtKonfirmasiPassword = findViewById(R.id.etKonfirmasiPassword)
        btnDaftar = findViewById(R.id.btnDaftar)
        btnLoginActivity = findViewById(R.id.btnLoginActivity)

        btnLoginActivity.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Set up Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.23/API_Mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Get instance of API interface
        api = retrofit.create(Api::class.java)

        btnDaftar.setOnClickListener {
            // Get user input
            val namaLengkap = txtNamaLengkap.text.toString().trim()
            val username = txtusername.text.toString().trim()
            val alamat = txtAlamat.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            val konfirmasiPassword = txtKonfirmasiPassword.text.toString().trim()

            // Check if input is valid
            if (namaLengkap.isEmpty() || username.isEmpty() || alamat.isEmpty() || password.isEmpty() || konfirmasiPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check apakah password sama dengan konfirmasi password
            if (password != konfirmasiPassword){
                Toast.makeText(this, "Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create new user object and send POST request to server
            val user = ModelUser(namaLengkap, username, alamat, password)
            api.registerUser(user).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // Registration successful, show success message
                        Toast.makeText(this@RegisterActivity, "Registrasi Sukses", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Registration failed, show error message
                        Toast.makeText(this@RegisterActivity, "Registrsi Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Registration failed due to network error, show error message
                    Toast.makeText(this@RegisterActivity, "Registrasi Fatal: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}