package id.mailstudio.foodcatalogue

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class FoodApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.BUILD_TYPE == "release") {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
    }
}