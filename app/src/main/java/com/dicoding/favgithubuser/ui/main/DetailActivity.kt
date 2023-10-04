package com.dicoding.favgithubuser.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModel.ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USER) ?: ""
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR) ?: ""
        Bundle().putString(EXTRA_USER, username)

        Log.d(TAG, "AvatarUrl in DetailActivity: $avatarUrl")
        Log.d(TAG, "Username in DetailActivity: $username")

        detailViewModel.getDetailUser(username)
        showLoading(true)

        detailViewModel.detailUser.observe(this){
            showLoading(false)
            if (it != null){
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(profileImage)
                    tvDetailName.text = it.name
                    tvDetailUsername.text = it.login
                    tvFollowers.text = resources.getString(R.string.followers_format, it.followers)
                    tvFollowing.text = resources.getString(R.string.following_format, it.following)
                }
            } else {
                Toast.makeText(this, R.string.error_loading_data, Toast.LENGTH_SHORT).show()
                binding.tvErrorMessage.text = getString(R.string.error_loading_data)
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        supportActionBar?.elevation = 0f

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tabLayout, position ->
            tabLayout.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.getDataByUsername(username).observe(this){
            isFavorite = it.isNotEmpty()
            val favoriteUser = FavoriteUserEntity.Item(username, avatarUrl)
            if (isFavorite){
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.favorite_removed)
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_border
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.favorite_added)
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite){
                    detailViewModel.delete(favoriteUser)
                    val resultIntent = Intent()
                    resultIntent.putExtra("data_deleted", true)
                    setResult(RESULT_OK, resultIntent)
                    Toast.makeText(this, R.string.favorite_removed, Toast.LENGTH_SHORT).show()
                } else {
                    detailViewModel.insert(favoriteUser)
                    Toast.makeText(this, R.string.favorite_added, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

    }
}