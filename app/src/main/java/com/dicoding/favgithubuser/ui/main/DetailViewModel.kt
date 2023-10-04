package com.dicoding.favgithubuser.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.favgithubuser.data.FavoriteUserRepository
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.data.remote.response.DetailUserResponse
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import com.dicoding.favgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel(){

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser : MutableLiveData<DetailUserResponse> = _detailUser

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _followings = MutableLiveData<List<ItemsItem>>()
    val followings: LiveData<List<ItemsItem>> = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(user: FavoriteUserEntity.Item){
        favoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUserEntity.Item){
        favoriteUserRepository.delete(user)
    }

    fun getDataByUsername(user: String) = favoriteUserRepository.getDataByUsername(user)

    init {
        getDetailUser()
    }

    fun getDetailUser(username: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null){
                    _detailUser.value = response.body()
                    val avatarUrl = response.body()?.avatarUrl ?: ""
                    Log.d(TAG, "AvatarUrl in getDetailUser: $avatarUrl")
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
//                Sebaiknya manfaatkan onFailure untuk menampilkan pesan error ke pengguna (contoh: Toast) daripada menampilkannya menggunakan log
            }
        })
    }

    fun getFollowers(username: String = ""){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _followers.value = response.body()
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowings(username: String = ""){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowings(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _followings.value = response.body()
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    class ViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory(){
        companion object{
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory{
                if (INSTANCE == null){
                    synchronized(ViewModelFactory::class.java){
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <view : ViewModel> create(modelClass: Class<view>): view {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
                return DetailViewModel(application) as view
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }

}