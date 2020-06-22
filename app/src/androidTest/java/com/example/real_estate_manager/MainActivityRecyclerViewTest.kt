package com.example.real_estate_manager

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.xwray.groupie.GroupieViewHolder
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityRecyclerViewTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun performClickOnRecyclerViewItem() {
        onView(withId(R.id.mainFragment_RecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GroupieViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.description_location_tv)).check(matches(withText("6 Rue du manoir, 72000 Le Mans, France")))
        onView(withId(R.id.description_surface_tv)).check(matches(withText("80 sqm")))
        onView(withId(R.id.description_rooms_tv)).check(matches(withText("3")))
        onView(withId(R.id.description_tv)).check(matches(withText("Beautiful flat, close to the university of Le Mans")))
        onView(withId(R.id.description_type_tv)).check(matches(withText("Flat")))
        onView(withId(R.id.description_price_tv)).check(matches(withText("100000")))
        onView(withId(R.id.description_agent_tv)).check(matches(withText("Ludovic Roland")))
        onView(withId(R.id.description_entry_date_tv)).check(matches(withText("24/08/2019")))

    }
}