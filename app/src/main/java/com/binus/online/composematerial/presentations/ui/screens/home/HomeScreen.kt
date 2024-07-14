package com.binus.online.composematerial.presentations.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.binus.online.composematerial.ui.theme.ComposeMaterialTheme

@Composable
fun HomeScreen(navController: NavController?) {
}

@Preview
@Composable
fun HomePreview() {
    ComposeMaterialTheme {
        HomeScreen(null)
    }
}