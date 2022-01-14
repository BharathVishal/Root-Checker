package com.roottools.rootchecker

import android.app.Application
import android.util.Log
import com.google.android.material.color.DynamicColors
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //For dynamic theming on Android 12 and above
        try {
            if (RootUtilities.isOSSandAbove) {
                DynamicColors.applyToActivitiesIfAvailable(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
