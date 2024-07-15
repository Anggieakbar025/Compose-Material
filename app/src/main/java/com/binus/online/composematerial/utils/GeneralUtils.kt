package com.binus.online.composematerial.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.text.SimpleDateFormat
import java.util.Locale

class GeneralUtils {
    companion object {
        @Composable
        public fun currentRoute(navController: NavHostController): String? {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            return navBackStackEntry?.destination?.route
        }

        fun formatDateTime(dateTime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateTime)
            return outputFormat.format(date)
        }
    }
}