package com.binus.online.composematerial.presentations.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.binus.online.composematerial.di.AppModule
import com.binus.online.composematerial.presentations.ui.screens.add.AddScreen
import com.binus.online.composematerial.presentations.ui.screens.detail.DetailScreen
import com.binus.online.composematerial.presentations.ui.screens.home.CatalogScreen
import com.binus.online.composematerial.presentations.ui.screens.home.HomeScreen
import com.binus.online.composematerial.presentations.ui.screens.login.LoginScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    val context = LocalContext.current
    val pref = AppModule.getAppPref(context)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Login.route) {
            if (pref.getLogin()) {
                navController.navigate(NavigationItem.Home.route) {
                    popUpTo(NavigationItem.Login.route) { inclusive = true }
                }
            } else LoginScreen(navController = navController)
        }
        composable(NavigationItem.Catalog.route) {
            CatalogScreen(navController = navController)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(NavigationItem.AddData.route) {
            AddScreen(navController = navController)
        }
        composable(
            NavigationItem.Detail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id")
            if (id != null) {
                DetailScreen(navController = navController, id = id)
            }
        }
    }
}