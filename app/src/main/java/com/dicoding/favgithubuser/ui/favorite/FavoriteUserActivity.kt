package com.dicoding.favgithubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.data.FavoriteUserRepository
import com.dicoding.favgithubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteUserAdapter by lazy {
        FavoriteUserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteUserViewModel> {
        FavoriteUserViewModel.FavoriteUserViewModelFactory(FavoriteUserRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.adapter = favoriteUserAdapter

        viewModel.getFavoriteUsers().observe(this){
            favoriteUserAdapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}