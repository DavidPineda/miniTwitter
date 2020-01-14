package com.example.minitwitter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.minitwitter.R
import com.example.minitwitter.common.Constants
import com.example.minitwitter.common.SharedPreferencesManager
import com.example.minitwitter.models.request.RequestLogin
import com.example.minitwitter.models.response.ResponseAuth
import com.example.minitwitter.services.ApiClient
import com.example.minitwitter.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnLogin: Button
    private lateinit var txtRegister: TextView
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        apiInit()
        findViews()
        events()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> goToLogin()
            R.id.txtRegister -> goToSignOut()
        }
    }

    private fun apiInit() {
        apiClient = ApiClient.instance!!
        apiService = apiClient.apiService
    }

    private fun findViews() {
        btnLogin = findViewById(R.id.btnLogin)
        txtRegister = findViewById(R.id.txtRegister)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
    }

    private fun events() {
        btnLogin.setOnClickListener(this)
        txtRegister.setOnClickListener(this)
    }

    private fun goToLogin() {
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()

        when {
            email.isBlank() -> txtEmail.error = "Email is required"
            password.isBlank() -> txtPassword.error = "Password is required"
            else -> {
                val requestLogin = RequestLogin(email, password)
                val call: Call<ResponseAuth> = apiService.doLogin(requestLogin)
                call.enqueue(object : Callback<ResponseAuth> {
                    override fun onFailure(call: Call<ResponseAuth>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity, "Wireless error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Session init successfully", Toast.LENGTH_SHORT).show()

                            response.body()?.token?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_AUTH_TOKEN, it) }
                            response.body()?.username?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_USERNAME, it) }
                            response.body()?.email?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL, it) }
                            response.body()?.photoUrl?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_PHOTO_URL, it) }
                            response.body()?.created?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_CREATED, it) }
                            response.body()?.active?.let { SharedPreferencesManager.setSomeBooleanValue(Constants.PREF_ACTIVE, it) }

                            val i = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Session init error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun goToSignOut() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }
}
