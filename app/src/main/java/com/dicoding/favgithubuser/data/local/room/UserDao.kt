package com.dicoding.favgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.favgithubuser.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: List<UserEntity>)

    @Update
    fun updateUsers(users: UserEntity)

    @Query("DELETE FROM users WHERE isFavorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username AND isFavorite = 1)")
    fun isUserFavorite(username: String): Boolean
}