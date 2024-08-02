package com.binus.online.composematerial.presentations.ui.navigation

enum class AppScreen {
    LOGIN,
    HOME,
    CATALOG,
    ADD_DATA,
    CHECK_LOCATION
}

sealed class NavigationItem(val route: String) {
    object Login: NavigationItem(AppScreen.LOGIN.name)
    object Home: NavigationItem(AppScreen.HOME.name)
    object Catalog: NavigationItem(AppScreen.CATALOG.name)
    object AddData: NavigationItem(AppScreen.ADD_DATA.name)
    object CheckLocation: NavigationItem(AppScreen.CHECK_LOCATION.name)
    object Detail: NavigationItem("detail/{id}") {
        fun createRoute(id: String): String = "detail/$id"
    }
}