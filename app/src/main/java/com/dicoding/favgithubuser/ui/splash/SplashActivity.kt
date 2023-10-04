package com.dicoding.favgithubuser.ui.splash

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.favgithubuser.R
import com.dicoding.favgithubuser.databinding.ActivitySplashBinding
import com.dicoding.favgithubuser.ui.main.MainActivity
import com.dicoding.favgithubuser.ui.setting.SettingPreferences
import com.dicoding.favgithubuser.ui.setting.dataStore
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashTimeOut:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)

        val settingPreferences = SettingPreferences.getInstance(dataStore)

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect{ isDarkModeActive ->
                if (isDarkModeActive){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.imageView.changeIconColor(R.color.on_secondary)
                    binding.tvSplashText.changeTextColor(R.color.on_secondary)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.imageView.changeIconColor(R.color.dark_on_secondary)
                    binding.tvSplashText.changeTextColor(R.color.dark_on_secondary)

                }
            }
        }
    }

    fun ImageView.changeIconColor(@ColorRes color: Int){
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }

    fun TextView.changeTextColor(@ColorRes color: Int){
        setTextColor(ContextCompat.getColor(this.context, color))
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}