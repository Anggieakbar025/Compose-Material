package com.binus.online.composematerial.presentations.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binus.online.composematerial.R
import com.binus.online.composematerial.di.AppModule
import com.binus.online.composematerial.presentations.ui.components.ConfirmationDialog
import com.binus.online.composematerial.presentations.ui.navigation.NavigationItem
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme

@Composable
fun LoginScreen(navController: NavController?) {
    val context = LocalContext.current
    val pref = AppModule.getAppPref(context)

    // State variables to store user input
    val userName = remember {
        mutableStateOf("")
    }
    val userPassword = remember {
        mutableStateOf("")
    }
    val openAlertDialog = remember { mutableStateOf(false) }

    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp)) {

        // Welcome message
        Text(
            text = "Hello,\nWelcome to the login page",
            fontSize = 25.sp,
            color = colorResource(id = R.color.teal_700),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 50.dp, 0.dp, 0.dp)
        )

        // Username input field
        OutlinedTextField(value = userName.value, onValueChange = {
            userName.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "person")
            },
            label = {
                Text(text = "username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        // Password input field
        OutlinedTextField(value = userPassword.value, onValueChange = {
            userPassword.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password")
            },
            label = {
                Text(text = "password")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Login button
        OutlinedButton(onClick = {
            if ("pengguna" == userName.value && "masuk" == userPassword.value) {
                pref.setLogin(true)
                navController?.navigate(route  = NavigationItem.Catalog.route)
            } else {
                openAlertDialog.value = true
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(
                text = "Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = colorResource(id = R.color.teal_700),
                )
        }
    }

    when {
        openAlertDialog.value -> {
            ConfirmationDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Login Gagal",
                dialogText = "Pastikan username dan password yang Anda masukkan benar.",
                icon = Icons.Default.Info
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ComposeMaterialTheme {
        LoginScreen(null)
    }
}
