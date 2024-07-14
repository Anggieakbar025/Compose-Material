package com.binus.online.composematerial.presentations.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.binus.online.composematerial.ui.theme.ComposeMaterialTheme

@Composable
fun DetailScreen(navController: NavController?, id: String) {

}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ComposeMaterialTheme {
        DetailScreen(null, "1")
    }
}
