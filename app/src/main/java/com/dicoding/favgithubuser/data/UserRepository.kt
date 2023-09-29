package com.dicoding.favgithubuser.data

import android.content.Context
import androidx.room.Room
import com.dicoding.favgithubuser.data.local.room.UserDatabase

class UserRepository (private val context: Context){
    private val repository = Room.databaseBuilder(context, UserDatabase::class.java, "github.db")
        .allowMainThreadQueries()
        .build()

    val userDao = repository.userDao()
}