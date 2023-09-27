package com.dicoding.favgithubuser.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.data.response.ItemsItem
import com.dicoding.favgithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel>()

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