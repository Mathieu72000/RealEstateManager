package com.example.real_estate_manager

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
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

        onView(withId(R.id.description_location_tv)).check(matches(withText("8 Place de la Madeleine, 75008 Paris, France")))
        onView(withId(R.id.description_surface_tv)).check(matches(withText("60 sqm")))
        onView(withId(R.id.description_rooms_tv)).check(matches(withText("2")))
        onView(withId(R.id.description_tv)).check(matches(withText("Absolutely beautiful house in Paris with very good lightning")))
        onView(withId(R.id.description_type_tv)).check(matches(withText("House")))
        onView(withId(R.id.description_price_tv)).check(matches(withText("290000")))
        onView(withId(R.id.description_agent_tv)).check(matches(withText("Patrick Moulin")))
        onView(withId(R.id.description_entry_date_tv)).check(matches(withText("27.04.2020")))

        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.mainFragment_RecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GroupieViewHolder>(
                1,
                click()
            )
        )

        onView(withId(R.id.description_location_tv)).check(matches(withText("8 Place de l'Église, 72380 Sainte-Jamme-sur-Sarthe, France")))
        onView(withId(R.id.description_surface_tv)).check(matches(withText("40 sqm")))
        onView(withId(R.id.description_rooms_tv)).check(matches(withText("2")))
        onView(withId(R.id.description_tv)).check(matches(withText("Practical apartment with nice view of the city church")))
        onView(withId(R.id.description_type_tv)).check(matches(withText("Flat")))
        onView(withId(R.id.description_price_tv)).check(matches(withText("70000")))
        onView(withId(R.id.description_agent_tv)).check(matches(withText("Patrick Moulin")))
        onView(withId(R.id.description_entry_date_tv)).check(matches(withText("29.04.2020")))


        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.mainFragment_RecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GroupieViewHolder>(
                2,
                click()
            )
        )

        onView(withId(R.id.description_location_tv)).check(matches(withText("Place de la République, 21000 Dijon, France")))
        onView(withId(R.id.description_surface_tv)).check(matches(withText("200 sqm")))
        onView(withId(R.id.description_rooms_tv)).check(matches(withText("5")))
        onView(withId(R.id.description_tv)).check(matches(withText("Huge house in Dijon with all convenience")))
        onView(withId(R.id.description_type_tv)).check(matches(withText("House")))
        onView(withId(R.id.description_price_tv)).check(matches(withText("380000")))
        onView(withId(R.id.description_agent_tv)).check(matches(withText("Benoît Hayung")))
        onView(withId(R.id.description_entry_date_tv)).check(matches(withText("30.04.2020")))
    }


}