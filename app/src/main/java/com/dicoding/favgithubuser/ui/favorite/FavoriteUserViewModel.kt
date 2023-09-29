package com.dicoding.favgithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.favgithubuser.data.FavoriteUserRepository
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUserEntity: FavoriteUserEntity.Item){
        mFavoriteUserRepository.insert(favoriteUserEntity)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity.Item>> = mFavoriteUserRepository.getAllFavoriteUsers()

    fun setFavorite(item: ItemsItem?){
        val favoriteUserEntity = FavoriteUserEntity.Item(
            username = item?.login ?: "",
            avatarUrl = item?.avatarUrl
        )
    }
}