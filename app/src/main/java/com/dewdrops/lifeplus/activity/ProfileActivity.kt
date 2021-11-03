package com.dewdrops.lifeplus.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.dewdrops.lifeplus.R
import com.dewdrops.lifeplus.datasource.UserDatabase
import com.dewdrops.lifeplus.datasource.model.Show
import com.dewdrops.lifeplus.datasource.model.User
import com.dewdrops.lifeplus.datasource.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LifePlus)
        setContentView(R.layout.activity_detail)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        val userName = sharedPreferences.getString("userName", "").toString()

        val userRepository = UserRepository(UserDatabase(this))
        val viewModelProviderFactory = UserViewModelProviderFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)

        var user: User? = null
        CoroutineScope(Dispatchers.Main).launch {
            user = userViewModel.getUserByUserName(userName)
        }.invokeOnCompletion {
            if (user != null) {
                tvName.text = "Name : "+user?.name
                tvUserName.text = "User Name : "+user?.userName
                tvPhone.text = "Phone : "+user?.phone
            } else {
                Toast.makeText(this, "User name or password is wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}