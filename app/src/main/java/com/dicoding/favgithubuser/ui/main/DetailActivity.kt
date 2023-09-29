package com.dicoding.favgithubuser.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.UserRepository
import com.dicoding.favgithubuser.data.local.entity.UserEntity
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.dicoding.favgithubuser.ui.favorite.FavoriteUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel>()
    private val viewModel by viewModels<FavoriteUserViewModel> {
        FavoriteUserViewModel.FavoriteUserViewModelFactory(UserRepository(this))
    }
    private val userRepository: UserRepository by lazy{
        UserRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val user = if (Build.VERSION.SDK_INT >= 33) {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        }

        if (user != null && detailViewModel.detailUser.value == null){
            val userEntity = UserEntity.Item(
                username = user.login.toString(),
                avatarUrl = user.avatarUrl,
                isFavorite = false
            )
            userRepository.userDao.insertUsers(userEntity)
            detailViewModel.getUser(user.login.toString())
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

        val item = intent.getParcelableExtra<UserEntity.Item>("username")

        if (item != null){
            binding.fabFavorite.setOnClickListener {
                val userEntity = UserEntity.Item(
                    username = item.username,
                    avatarUrl = item.avatarUrl,
                    isFavorite = true
                )
                userRepository.userDao.insertUsers(userEntity)
                viewModel.setFavorite(userEntity)
                Log.d("DetailActivity", "Favorite item added")
            }
        } else {
            Log.d("DetailActivity", "Item is null, cannot add to favorites")
        }

        val fabFavorite = binding.fabFavorite

        viewModel.findFavorite(item?.username ?: ""){
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(fabFavorite.context, R.drawable.ic_favorite))
            if (item != null){
                Log.d("DetailActivity", "Item sudah ada di favorit")
            } else {
                Log.d("DetailActivity", "Item belum ada di favorit")
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

    }
}