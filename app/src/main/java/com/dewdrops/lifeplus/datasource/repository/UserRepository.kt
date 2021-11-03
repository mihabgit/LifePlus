package com.dewdrops.lifeplus.datasource.repository

import com.dewdrops.lifeplus.datasource.UserDatabase
import com.dewdrops.lifeplus.datasource.dao.UserDao
import com.dewdrops.lifeplus.datasource.model.User

class UserRepository(private val db: UserDatabase) {

    suspend fun insertUser(user: User) {
        db.userDao.insertUser(user)
    }

    suspend fun getUserByUserName(userName: String): User {
        return db.userDao.getUserByUserName(userName)
    }

}