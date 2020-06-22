package com.example.real_estate_manager

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.xwray.groupie.GroupieViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUpdateTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testUpdatingHouse() {

        Espresso.onView(ViewMatchers.withId(R.id.mainFragment_RecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GroupieViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.modify)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_description_editText))
            .perform(ViewActions.click())
            .perform(
                ViewActions.replaceText("Nice apartment with all near convenience"),
                ViewActions.closeSoftKeyboard()
            ).check(
                ViewAssertions.matches(ViewMatchers.withText("Nice apartment with all near convenience"))
            )
        Espresso.onView(ViewMatchers.withId(R.id.form_submit_button))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                ViewActions.click()
            )

        Espresso.onView(ViewMatchers.withId(R.id.modify)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.form_description_editText))
            .perform(ViewActions.click())
            .perform(
                ViewActions.replaceText("Beautiful flat, close to the university of Le Mans"),
                ViewActions.closeSoftKeyboard()
            ).check(
                ViewAssertions.matches(ViewMatchers.withText("Beautiful flat, close to the university of Le Mans"))
            )
        Espresso.onView(ViewMatchers.withId(R.id.form_submit_button))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                ViewActions.click()
            )
    }

}