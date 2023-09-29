package com.dicoding.favgithubuser.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.data.remote.response.ItemsItem

@Database(entities = [UserEntity.Item::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}