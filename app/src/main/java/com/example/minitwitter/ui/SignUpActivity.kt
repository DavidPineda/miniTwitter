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
import com.example.minitwitter.models.request.RequestSignUp
import com.example.minitwitter.models.response.ResponseAuth
import com.example.minitwitter.services.ApiClient
import com.example.minitwitter.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnRegister: Button;
    private lateinit var txtLogin: TextView;
    private lateinit var txtUser: EditText;
    private lateinit var txtEmail: EditText;
    private lateinit var txtPassword: EditText;
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        apiInit()
        findViews()
        events()
    }

    private fun apiInit() {
        apiClient = ApiClient.instance!!
        apiService = apiClient.apiService
    }

    private fun findViews() {
        btnRegister = findViewById(R.id.btnRegister)
        txtLogin = findViewById(R.id.txtLogin)
        txtUser = findViewById(R.id.txtUsername)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
    }

    private fun events() {
        btnRegister.setOnClickListener(this)
        txtLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRegister -> goToSignUp()
            R.id.txtLogin -> goToLogin()
        }
    }

    private fun goToSignUp() {
        val username = txtUser.text.toString()
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        when {
            username.isBlank() -> txtUser.error = "Username is required"
            email.isBlank() -> txtEmail.error = "Email is required"
            password.isBlank() -> txtPassword.error = "Password is required"
            else -> {
                val requestSignUp = RequestSignUp(username, email, password, code = "UDEMYANDROID")
                val call: Call<ResponseAuth> = apiService.doSignUp(requestSignUp)
                call.enqueue(object : Callback<ResponseAuth> {
                    override fun onFailure(call: Call<ResponseAuth>?, t: Throwable?) {
                        Toast.makeText(this@SignUpActivity, "Wireless error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@SignUpActivity, "User create successfully", Toast.LENGTH_SHORT).show()

                            response.body()?.token?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_AUTH_TOKEN, it) }
                            response.body()?.username?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_USERNAME, it) }
                            response.body()?.email?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL, it) }
                            response.body()?.photoUrl?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_PHOTO_URL, it) }
                            response.body()?.created?.let { SharedPreferencesManager.setSomeStringValue(Constants.PREF_CREATED, it) }
                            response.body()?.active?.let { SharedPreferencesManager.setSomeBooleanValue(Constants.PREF_ACTIVE, it) }

                            val i = Intent(this@SignUpActivity, HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, "User register error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
