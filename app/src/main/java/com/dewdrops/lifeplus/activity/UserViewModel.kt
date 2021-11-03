package com.dewdrops.lifeplus.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dewdrops.lifeplus.datasource.model.User
import com.dewdrops.lifeplus.datasource.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun getUserByUserName(userName: String): User = userRepository.getUserByUserName(userName)

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}

class UserViewModelProviderFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}