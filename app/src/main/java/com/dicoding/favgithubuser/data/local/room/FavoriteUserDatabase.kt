package com.dicoding.favgithubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity.Item::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase{
            if (INSTANCE == null){
                synchronized(FavoriteUserDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserDatabase::class.java, "favoriteUser_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserDatabase
        }
    }
}