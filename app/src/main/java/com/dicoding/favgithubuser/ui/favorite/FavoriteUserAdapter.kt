package com.dicoding.favgithubuser.ui.favorite

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.ui.favorite.FavoriteUserAdapter.MyViewHolder
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding

class FavoriteUserAdapter(private val onFavoriteClick: (UserEntity) -> Unit) : ListAdapter<UserEntity, MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ActivityDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    class MyViewHolder(val binding: ActivityDetailBinding) : RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(users: UserEntity){
            binding.tvDetailUsername.text = users.username
            Log.d("FaforiteUserAdapter", "URL profile: ${users.avatarUrl}")
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .into(binding.profileImage)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)

        val fabFavorite = holder.binding.fabFavorite
        if (users.isFavorite){
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(fabFavorite.context, R.drawable.ic_favorite))
        } else {
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(fabFavorite.context, R.drawable.ic_favorite_border))
        }
        fabFavorite.setOnClickListener {
            onFavoriteClick(users)
        }
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}