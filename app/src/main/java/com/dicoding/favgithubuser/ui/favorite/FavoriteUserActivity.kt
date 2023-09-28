package com.dicoding.favgithubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel: FavoriteUserViewModel by viewModels()

    private val favoriteUserAdapter = FavoriteUserAdapter { user ->
        if (user.isFavorite){
            viewModel.deleteUser(user)
        } else {
            viewModel.saveUser(user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavoriteUser.adapter = favoriteUserAdapter

        viewModel.getFavoriteUsers().observe(this) { favoriteUsers ->
            binding.progressBar.visibility = View.GONE
            favoriteUserAdapter.submitList(favoriteUsers)
        }

        binding.rvFavoriteUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteUserAdapter
        }
    }
}