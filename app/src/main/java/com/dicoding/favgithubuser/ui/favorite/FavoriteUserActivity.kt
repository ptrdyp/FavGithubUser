package com.dicoding.favgithubuser.ui.favorite

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.favgithubuser.ui.main.DetailActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        val favoriteUserViewModel = obtainViewModel(this)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this){
            setFavoriteUser(it)
        }

        favoriteUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun setFavoriteUser(favoriteUserEntities: List<FavoriteUserEntity.Item>){
        val items = arrayListOf<FavoriteUserEntity.Item>()
        favoriteUserEntities.map {
            val item = FavoriteUserEntity.Item(
                username = it.username,
                avatarUrl = it.avatarUrl
            )
            items.add(item)
        }
        val adapter = FavoriteUserAdapter(items)
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.setHasFixedSize(true)
        binding.rvFavoriteUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavoriteUserEntity.Item) {
                Log.d(TAG, "AvatarUrl before starting DetailActivity: ${data.avatarUrl}")
                startActivity(
                    Intent(this@FavoriteUserActivity, DetailActivity::class.java)
                        .putExtra(DetailActivity.EXTRA_USER, data.username)
                        .putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                )
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel{
        val factory = FavoriteUserViewModel.ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
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