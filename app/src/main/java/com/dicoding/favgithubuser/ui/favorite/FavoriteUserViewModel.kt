package com.dicoding.favgithubuser.ui.favorite

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.favgithubuser.data.FavoriteUserRepository
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.data.local.room.FavoriteUserDao
import com.dicoding.favgithubuser.data.local.room.FavoriteUserDatabase

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val favoriteUserDao = FavoriteUserDatabase.getDatabase(application).favoriteUserDao()

    val favoriteUsers: LiveData<List<FavoriteUserEntity.Item>> = favoriteUserDao.getAllFavorites()
}