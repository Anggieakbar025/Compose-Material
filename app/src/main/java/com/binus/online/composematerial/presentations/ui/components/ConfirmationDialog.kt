package com.binus.online.composematerial.presentations.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binus.online.composematerial.R
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = dialogText
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.teal_200), RoundedCornerShape(8.dp)),
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold,
                    text = "OK"
                )
            }
        },
        containerColor = Color.White
    )
}

@Preview
@Composable
fun PreviewConfirmationDialog() {
    ComposeMaterialTheme {
        ConfirmationDialog(
            onDismissRequest = { },
            onConfirmation = { },
            dialogTitle = "Login Gagal",
            dialogText = "Pastikan username / password benar.",
            icon = Icons.Default.Info
        )
    }
}