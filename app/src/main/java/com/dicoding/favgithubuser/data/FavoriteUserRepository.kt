package com.dicoding.favgithubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.data.local.room.FavoriteUserDao
import com.dicoding.favgithubuser.data.local.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application){
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity.Item>> = mFavoriteUserDao.getAllFavorites()

    fun insert(username: FavoriteUserEntity.Item){
        executorService.execute { mFavoriteUserDao.insertUsers(username) }
    }

    fun getDataByUsername(username: String): LiveData<List<FavoriteUserEntity.Item>> = mFavoriteUserDao.getDataByUsername(username)

    fun delete(username: FavoriteUserEntity.Item){
        executorService.execute {
            mFavoriteUserDao.delete(username)
        }
    }
}