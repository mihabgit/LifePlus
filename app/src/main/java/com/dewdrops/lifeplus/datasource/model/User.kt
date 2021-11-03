package com.dewdrops.lifeplus.datasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey()
    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "phone")
    val phone: String
)
