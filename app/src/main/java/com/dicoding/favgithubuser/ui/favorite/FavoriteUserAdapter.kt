package com.dicoding.favgithubuser.ui.favorite

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.databinding.ItemUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserAdapter(
    private val listFavorite: MutableList<FavoriteUserEntity.Item>
) : RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteUserEntity.Item)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(users: FavoriteUserEntity.Item){
            binding.tvUsername.text = users.username
            Log.d("FavoriteUserAdapter", "URL profile: ${users.avatarUrl}")
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .into(binding.circleAvatar)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = listFavorite[position]
        holder.bind(users)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context,  DetailActivity::class.java)
            intentDetail.putExtra("username", users.username)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, users)
            onItemClickCallback.onItemClicked(users)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorite.size
}