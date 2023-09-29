package com.dicoding.favgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorite_user ORDER BY username ASC")
    fun getAllFavorites(): LiveData<List<FavoriteUserEntity.Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: FavoriteUserEntity.Item)

    @Delete
    fun delete(users: FavoriteUserEntity.Item)
}