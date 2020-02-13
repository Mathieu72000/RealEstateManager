package com.example.real_estate_manager
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication: MultiDexApplication() {

    override fun onCreate() {
        startKoin { androidContext(this@MyApplication) }
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        Places.initialize(this, "AIzaSyDTugJk9MDW3i_-BLQpeokXenKvOdcrxAw")
        Stetho.initializeWithDefaults(this)
    }
}