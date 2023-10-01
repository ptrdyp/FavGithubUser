package com.dicoding.favgithubuser.ui.favorite

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private lateinit var favoriteUserAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, 0)
        binding.rvFavoriteUser.addItemDecoration(itemDecoration)
        binding.rvFavoriteUser.setHasFixedSize(true)

        favoriteUserViewModel = ViewModelProvider(this)[FavoriteUserViewModel::class.java]

        favoriteUserAdapter = FavoriteUserAdapter(emptyList())
        binding.rvFavoriteUser.adapter = favoriteUserAdapter

        favoriteUserViewModel.favoriteUsers.observe(this){ users ->
            Log.d("FavoriteViewModel", "Received ${users.size} users")
            favoriteUserAdapter.listFavorite = users
            favoriteUserAdapter.notifyDataSetChanged()
        }

    }
}