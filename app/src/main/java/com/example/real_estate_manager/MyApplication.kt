package com.example.real_estate_manager

import android.content.res.Resources
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.batch.android.Batch
import com.batch.android.BatchActivityLifecycleHelper
import com.batch.android.Config
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : MultiDexApplication() {

    class Configuration(resources: Resources) {

        val isPhone = resources.getBoolean(R.bool.phone)
        val isPhablet = resources.getBoolean(R.bool.phablet)
        val isTablet = resources.getBoolean(R.bool.tablet)
    }

    val configuration by lazy { Configuration(resources) }

    override fun onCreate() {
        startKoin { androidContext(this@MyApplication) }
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Places.initialize(this, "AIzaSyC9J2Kn_jcqaQ9xnQLvf34Dl8VwlFQWKCs")

        if (!isRoboUnitTests()) {
            Stetho.initializeWithDefaults(this)
        }

        Batch.setConfig(Config("DEV5EB6CA9E2BAF5100E03F1F2955C"))
        registerActivityLifecycleCallbacks(BatchActivityLifecycleHelper())
        Batch.User.getInstallationID()
    }

    private fun isRoboUnitTests(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }
}