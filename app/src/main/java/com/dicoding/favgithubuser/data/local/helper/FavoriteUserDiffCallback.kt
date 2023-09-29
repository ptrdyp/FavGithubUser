package com.dicoding.favgithubuser.data.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity

class FavoriteUserDiffCallback(private val oldFavoriteList: List<FavoriteUserEntity.Item>, private val newFavoriteList: List<FavoriteUserEntity.Item>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.username == newFavorite.username && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }
}