package com.dicoding.favgithubuser.ui.favorite

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.favgithubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteUserActivityTest {

    @Before
    fun setUp(){
        ActivityScenario.launch(FavoriteUserActivity::class.java)
    }

    @Test
    fun testFavoriteUserListDisplay(){
        Espresso.onView(ViewMatchers.withId(R.id.rv_favorite_user))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}