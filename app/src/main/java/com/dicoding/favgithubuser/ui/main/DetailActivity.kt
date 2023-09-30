package com.dicoding.favgithubuser.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER) ?: ""
        val avatar = intent.getStringExtra(EXTRA_AVATAR) ?: ""
        Bundle().putString(EXTRA_USER, username)

        supportActionBar?.title = "Detail User"

        val user = if (Build.VERSION.SDK_INT >= 33) {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        }

        if (user != null && detailViewModel.detailUser.value == null){
            detailViewModel.getDetailUser(user.login.toString())
        }

        detailViewModel.detailUser.observe(this) { detailUser ->
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .into(binding.profileImage)
            binding.tvDetailName.text = detailUser.name
            binding.tvDetailUsername.text = detailUser.login
            binding.tvFollowers.text = "${detailUser.followers} Followers"
            binding.tvFollowing.text = "${detailUser.following} Followings"

            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
            sectionsPagerAdapter.username = detailUser.login.toString()
            binding.viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = binding.tabs

            TabLayoutMediator(tabs, binding.viewPager){ tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        favoriteViewModel.getDataByUsername(username).observe(this){
            isFavorite = it.isNotEmpty()
            val favoriteUser = FavoriteUserEntity.Item(username, avatar)
            if (isFavorite){
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_border
                    )
                )
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite){
                    favoriteViewModel.delete(favoriteUser)
                    Toast.makeText(this, R.string.favorite_removed, Toast.LENGTH_SHORT).show()
                } else {
                    favoriteViewModel.insert(favoriteUser)
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