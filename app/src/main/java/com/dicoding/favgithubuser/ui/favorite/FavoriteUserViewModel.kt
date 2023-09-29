package com.dicoding.favgithubuser.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.favgithubuser.data.UserRepository
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class FavoriteUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    fun setFavorite(item: UserEntity.Item?){
        viewModelScope.launch {
            item?.let {
                isFavorite = it.isFavorite
                if (isFavorite){
                    userRepository.userDao.delete(item)
                    resultDeleteFavorite.value = true
                } else {
                    userRepository.userDao.insertUsers(item)
                    resultSuccessFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(username: String, listenFavorite: () -> Unit){
        viewModelScope.launch {
            val user = userRepository.userDao.findByUsername(username)
            if (user != null){
                listenFavorite()
                isFavorite = true
            }
        }
    }

    class FavoriteUserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteUserViewModel(userRepository) as T
    }

    fun getFavoriteUsers() = userRepository.userDao.getUsers()
}