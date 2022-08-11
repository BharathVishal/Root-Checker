package com.roottools.rootchecker

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

        val intent = Intent(actvityContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}