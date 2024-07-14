package com.binus.online.composematerial.presentations.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.binus.online.composematerial.di.AppModule
import com.binus.online.composematerial.presentations.ui.navigation.AppNavHost
import com.binus.online.composematerial.presentations.ui.navigation.NavigationItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavHost(
                navController = navController,
                startDestination = NavigationItem.Login.route
            )
        }
    }
}