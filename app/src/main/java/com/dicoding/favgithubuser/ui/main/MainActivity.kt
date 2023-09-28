package com.dicoding.favgithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.databinding.ActivityMainBinding
import com.dicoding.favgithubuser.ui.favorite.FavoriteUserActivity
import com.dicoding.favgithubuser.ui.setting.SettingActivity
import com.dicoding.favgithubuser.ui.setting.SettingPreferences
import com.dicoding.favgithubuser.ui.setting.dataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        start setting
        val settingPreferences = SettingPreferences.getInstance(dataStore)

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect{ isDarkModeActive ->
                if (isDarkModeActive){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
//        end setting

        with(binding){
            searchView.setupWithSearchBar(searchBar)
        }

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        val userAdapter = UserAdapter()

        mainViewModel.listUser.observe(this){ listUsers ->
            userAdapter.submitList(listUsers)
        }

        binding.rvUser.adapter = userAdapter

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            val query = binding.searchView.text.toString()
            binding.searchBar.text = query
            mainViewModel.searchUser(query)
            binding.searchView.hide()
            true
        }

        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                R.id.mode -> {
                    val intentMode = Intent(this, SettingActivity::class.java)
                    startActivity(intentMode)
                    true
                }
                R.id.list_fav -> {
                        val intentFav = Intent(this, FavoriteUserActivity::class.java)
                    startActivity(intentFav)
                    true
                }
                else -> false
            }
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}