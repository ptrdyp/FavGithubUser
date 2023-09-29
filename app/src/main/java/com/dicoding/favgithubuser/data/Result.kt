package com.dicoding.favgithubuser.data

sealed class Result {
    data class Success<out T>(val data: T) : Result()
    data class Error(val error: String) : Result()
    data class Loading(val isLoading: Boolean) : Result()
}