package com.dicoding.favgithubuser.data

import com.dicoding.favgithubuser.data.local.entity.UserEntity

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}