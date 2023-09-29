package com.dicoding.favgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.data.remote.response.GithubResponse
import com.dicoding.favgithubuser.data.remote.response.ItemsItem

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getUsers(): LiveData<MutableList<UserEntity.Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: UserEntity.Item)

    @Query("SELECT * FROM users WHERE username LIKE :username LIMIT 1")
    fun findByUsername(username: String): UserEntity.Item

    @Delete
    fun delete(users: UserEntity.Item)
}