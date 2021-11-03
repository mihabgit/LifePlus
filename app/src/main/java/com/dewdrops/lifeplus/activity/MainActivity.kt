package com.dewdrops.lifeplus.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dewdrops.lifeplus.R
import com.dewdrops.lifeplus.datasource.UserDatabase
import com.dewdrops.lifeplus.datasource.model.User
import com.dewdrops.lifeplus.datasource.repository.UserRepository
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var btnRegistration: Button
    private lateinit var btnLogin: Button
    private lateinit var userViewModel: UserViewModel
    private lateinit var tilUserName: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LifePlus)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        val userRepository = UserRepository(UserDatabase(this))
        val viewModelProviderFactory = UserViewModelProviderFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)


        tilUserName = findViewById(R.id.tilUserName)
        tilPassword = findViewById(R.id.tilPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegistration = findViewById(R.id.btnRegistration)

        btnLogin.setOnClickListener {
            getUserDataAndValidateUser()
        }

        btnRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun getUserDataAndValidateUser() {
        if (isValid()) {
            val userName = tilUserName.editText?.text.toString().trim()
            val password = tilPassword.editText?.text.toString().trim()

            var user: User? = null

            CoroutineScope(Main).launch {
                user = userViewModel.getUserByUserName(userName)
            }.invokeOnCompletion {
                if (user?.userName == userName && user?.password == password) {
                    startActivity(Intent(this, DashboardActivity::class.java))

                    editor.putString("userName", user?.userName)
                    editor.commit()
                } else {
                    Toast.makeText(this, "User name or password is wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValid(): Boolean {
        val userName = tilUserName.editText?.text.toString().trim()
        val password = tilPassword.editText?.text.toString().trim()

        if (userName.isBlank()) {
            tilUserName.error = "Enter username"
            return false
        } else {
            tilUserName.error = ""
        }

        if (password.isBlank()) {
            tilPassword.error = "Enter password"
            return false
        } else {
            tilPassword.error = ""
        }

        return true
    }
}