package com.dicoding.favgithubuser.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.favgithubuser.data.FavoriteUserRepository
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(user: FavoriteUserEntity.Item){
        favoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUserEntity.Item){
        favoriteUserRepository.delete(user)
    }

    fun getDataByUsername(username: String) = favoriteUserRepository.getDataByUsername(username)

    class ViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory(){
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
                return FavoriteViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

}