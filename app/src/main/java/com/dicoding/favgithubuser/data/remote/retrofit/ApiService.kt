package com.dicoding.favgithubuser.data.remote.retrofit

import com.dicoding.favgithubuser.data.remote.response.DetailUserResponse
import com.dicoding.favgithubuser.data.remote.response.GithubResponse
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{login}/following")
    fun getUserFollowings(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}