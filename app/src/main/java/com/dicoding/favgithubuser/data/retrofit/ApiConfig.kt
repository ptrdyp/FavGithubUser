package com.dicoding.favgithubuser.data.retrofit

import com.dicoding.favgithubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService{
            val authInterceptor = Interceptor{ chain ->
                val req = chain.request()
                val mySuperSercretKey = BuildConfig.KEY
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", mySuperSercretKey)
                    .build()
                chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}