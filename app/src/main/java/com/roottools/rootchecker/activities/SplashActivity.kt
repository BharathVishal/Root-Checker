package com.roottools.rootchecker.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities

public class SplashActivity : AppCompatActivity() {
    private var actvityContext: Context? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (RootUtilities.isOSSandAbove) {
            val splashScreen = installSplashScreen()
        }

        actvityContext = this@SplashActivity


        //For testing purpose
        //1 - Default Main Activity with xml
        //2 - Main activity with Jetpack Compose
        val activityTypeToLaunch = 2

        if (activityTypeToLaunch == 1) {
            val intent = Intent(actvityContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(actvityContext, MainActivityCompose::class.java)
            startActivity(intent)
            finish()
        }
    }
}