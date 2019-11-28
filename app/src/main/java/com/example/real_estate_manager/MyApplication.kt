package com.example.real_estate_manager
import androidx.multidex.MultiDexApplication
import com.google.android.libraries.places.api.Places

class MyApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, "AIzaSyDY1yomFbADhonkDJg59g1qfpFDKkDuNiw")
    }
}