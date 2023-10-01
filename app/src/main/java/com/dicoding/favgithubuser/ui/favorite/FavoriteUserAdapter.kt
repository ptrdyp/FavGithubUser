package com.dicoding.favgithubuser.ui.favorite

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.dicoding.favgithubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.favgithubuser.databinding.ItemUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserAdapter(
    var listFavorite: List<FavoriteUserEntity.Item>
) : RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ){
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvItem.text = listFavorite[position].username
        Glide.with(holder.imageView.context).load(listFavorite[position].avatarUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context,  DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, listFavorite[position].username)
            intentDetail.putExtra(DetailActivity.EXTRA_AVATAR, listFavorite[position].avatarUrl)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = listFavorite.size
}