package com.dicoding.favgithubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.favgithubuser.data.UserRepository
import com.dicoding.favgithubuser.data.local.entity.UserEntity

class FavoriteUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getGithubUsers() = userRepository.getGithubUsers()

    fun getFavoriteUsers() = userRepository.getFavoriteUsers()

    fun saveUser(users: UserEntity){
        userRepository.setFavoriteUsers(users, true)
    }

    fun deleteUser(users: UserEntity){
        userRepository.setFavoriteUsers(users, false)
    }
}