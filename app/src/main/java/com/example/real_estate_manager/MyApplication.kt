package com.example.real_estate_manager
import androidx.multidex.MultiDexApplication
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class MyApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, "AIzaSyDTugJk9MDW3i_-BLQpeokXenKvOdcrxAw")

    }
}