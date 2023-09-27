package com.dicoding.favgithubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.favgithubuser.data.response.DetailUserResponse
import com.dicoding.favgithubuser.data.response.GithubResponse
import com.dicoding.favgithubuser.data.response.ItemsItem
import com.dicoding.favgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){

    private val _user = MutableLiveData<GithubResponse>()
    val user: LiveData<GithubResponse> = _user

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser : LiveData<DetailUserResponse> = _detailUser

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _followings = MutableLiveData<List<ItemsItem>>()
    val followings: LiveData<List<ItemsItem>> = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(login: String){
        getDetailUser(login)
    }

    fun getDetailUser(username: String) {
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
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowers(login: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(login)
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

    fun getFollowings(login: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowings(login)
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

    companion object{
        private const val TAG = "DetailViewModel"
    }

}