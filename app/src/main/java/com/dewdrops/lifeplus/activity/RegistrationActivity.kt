package com.dewdrops.lifeplus.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.dewdrops.lifeplus.R
import com.dewdrops.lifeplus.datasource.UserDatabase
import com.dewdrops.lifeplus.datasource.model.User
import com.dewdrops.lifeplus.datasource.repository.UserRepository
import com.google.android.material.textfield.TextInputLayout


class RegistrationActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilUserName: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var btnRegistration: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LifePlus)
        setContentView(R.layout.activity_registration)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val userRepository = UserRepository(UserDatabase(this))
        val viewModelProviderFactory = UserViewModelProviderFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)

        tilName = findViewById(R.id.tilName)
        tilUserName = findViewById(R.id.tilUserName)
        tilPassword = findViewById(R.id.tilPassword)
        tilPhone = findViewById(R.id.tilPhone)
        btnRegistration = findViewById(R.id.btnRegistration)

        btnRegistration.setOnClickListener {
            if (isValid()) {
                val user = getUserData()
                userViewModel.insertUser(user)
                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun getUserData(): User {
        val name = tilName.editText?.text.toString()
        val userName = tilUserName.editText?.text.toString()
        val password = tilPassword.editText?.text.toString()
        val phone = tilPhone.editText?.text.toString()

        return User(name, userName, password, phone)
    }

    private fun isValid(): Boolean {
        val name = tilName.editText?.text.toString().trim()
        val userName = tilUserName.editText?.text.toString().trim()
        val password = tilPassword.editText?.text.toString().trim()
        val phone = tilPhone.editText?.text.toString().trim()

        if (name.isBlank()) {
            tilName.error = "Enter name"
            return false
        } else {
            tilName.error = ""
        }

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

        if (phone.isBlank()) {
            tilPhone.error = "Enter phone"
            return false
        } else {
            tilPhone.error = ""
        }

        return true
    }
}