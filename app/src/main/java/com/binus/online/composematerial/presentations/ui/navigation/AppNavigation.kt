package com.binus.online.composematerial.presentations.ui.navigation

enum class AppScreen {
    LOGIN,
    HOME,
    CATALOG,
    ADD_DATA
}

sealed class NavigationItem(val route: String) {
    object Login: NavigationItem(AppScreen.LOGIN.name)
    object Home: NavigationItem(AppScreen.HOME.name)
    object Catalog: NavigationItem(AppScreen.CATALOG.name)
    object AddData: NavigationItem(AppScreen.ADD_DATA.name)
    object Detail: NavigationItem("detail/{id}")
}