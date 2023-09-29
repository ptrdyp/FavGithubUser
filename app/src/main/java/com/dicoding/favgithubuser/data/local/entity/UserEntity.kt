package com.dicoding.favgithubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class UserEntity(
    val items: MutableList<Item>
){
    @Parcelize
    @Entity(tableName = "users")
    data class Item (

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "username")
        var username: String = "",

        @ColumnInfo(name = "avatarUrl")
        var avatarUrl: String? = null,

        @ColumnInfo(name = "isFavorite")
        var isFavorite: Boolean

    ) : Parcelable
}
