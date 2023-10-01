package com.dicoding.favgithubuser.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dicoding.favgithubuser.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    val animationIdlingResource: IdlingResource = AnimationIdlingResource()
    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(animationIdlingResource)

        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testSearchUser(){
        Espresso.onView(ViewMatchers.withId(R.id.searchView))
            .perform(ViewActions.typeText("ptrdyp"))

        Espresso.onView(ViewMatchers.withId(R.id.searchBar))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rvUser))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(animationIdlingResource)
    }

    private class AnimationIdlingResource : IdlingResource {
        private val idleNow = AtomicBoolean(true)
        private lateinit var resourceCallback: ResourceCallback

        override fun getName(): String {
            return AnimationIdlingResource::class.java.name
        }

        override fun isIdleNow(): Boolean {
            return idleNow.get()
        }

        override fun registerIdleTransitionCallback(callback: ResourceCallback) {
            this.resourceCallback = callback
        }
    }
}