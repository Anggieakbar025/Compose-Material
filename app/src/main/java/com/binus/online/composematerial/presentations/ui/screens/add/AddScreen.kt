package com.binus.online.composematerial.presentations.ui.screens.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.binus.online.composematerial.ui.theme.ComposeMaterialTheme

@Composable
fun AddScreen(navController: NavController?) {

}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ComposeMaterialTheme {
        AddScreen(null)
    }
}
