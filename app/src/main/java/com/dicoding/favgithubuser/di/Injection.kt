package com.dicoding.favgithubuser.di

import android.content.Context
import com.dicoding.favgithubuser.data.UserRepository
import com.dicoding.favgithubuser.data.local.room.UserDatabase
import com.dicoding.favgithubuser.data.remote.retrofit.ApiConfig
import com.dicoding.favgithubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository{
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }
}