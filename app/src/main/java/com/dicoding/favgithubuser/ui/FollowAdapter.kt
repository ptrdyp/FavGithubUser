package com.dicoding.favgithubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.data.response.ItemsItem
import com.dicoding.favgithubuser.databinding.ItemUserBinding

class FollowAdapter(private val items: List<ItemsItem>) : RecyclerView.Adapter<FollowAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.tvUsername.text = user.login

            Glide.with(binding.circleAvatar.context)
                .load(user.avatarUrl)
                .into(binding.circleAvatar)
        }
    }

}