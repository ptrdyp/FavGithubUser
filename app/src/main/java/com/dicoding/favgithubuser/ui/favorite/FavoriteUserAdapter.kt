package com.dicoding.favgithubuser.ui.favorite

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.dicoding.favgithubuser.databinding.ItemUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserAdapter(
    private val data: MutableList<UserEntity.Item> = mutableListOf(),
    private val onFavoriteClick: (UserEntity.Item) -> Unit
) : RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {

    fun setData(data: MutableList<UserEntity.Item>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(users: UserEntity.Item){
            binding.tvUsername.text = users.username
            Log.d("FavoriteUserAdapter", "URL profile: ${users.avatarUrl}")
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .into(binding.circleAvatar)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = data[position]
        holder.bind(users)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context,  DetailActivity::class.java)
            intentDetail.putExtra("username", users.username)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, users)
            onFavoriteClick(users)
        }
    }

    override fun getItemCount(): Int = data.size
}