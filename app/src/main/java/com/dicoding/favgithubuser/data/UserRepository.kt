package com.dicoding.favgithubuser.data

import androidx.lifecycle.MediatorLiveData
import com.dicoding.favgithubuser.BuildConfig
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.data.local.room.UserDao
import com.dicoding.favgithubuser.data.remote.response.GithubResponse
import com.dicoding.favgithubuser.data.remote.retrofit.ApiService
import com.dicoding.favgithubuser.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<UserEntity>>>()

    fun getGithubUsers(): LiveData<Result<List<UserEntity>>>{
        result.value = Result.Loading
        val client = apiService.getGithubUser(BuildConfig.KEY)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful){
                    val users = response.body()?.items
                    val userList = ArrayList<UserEntity>()
                    appExecutors.diskIO.execute {
                        users?.forEach { user ->
                            val isFavorite = userDao.isUserFavorite(user.login.toString())
                            val users = UserEntity (
                                user.login.toString(),
                                user.avatarUrl,
                                isFavorite
                            )
                            userList.add(users)
                        }
                        userDao.deleteAll()
                        userDao.insertUsers(userList)
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = userDao.getUsers()
        result.addSource(localData){ newData: List<UserEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getFavoriteUsers(): LiveData<List<UserEntity>>{
        return userDao.getFavoriteUsers()
    }

    fun setFavoriteUsers(users: UserEntity, favoriteState: Boolean){
        appExecutors.diskIO.execute {
            users.isFavorite = favoriteState
            userDao.updateUsers(users)
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}