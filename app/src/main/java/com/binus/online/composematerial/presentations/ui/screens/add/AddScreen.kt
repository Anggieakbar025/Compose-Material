package com.binus.online.composematerial.presentations.ui.screens.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.binus.online.composematerial.R
import com.binus.online.composematerial.database.DBHandler
import com.binus.online.composematerial.domain.model.VictimModel
import com.binus.online.composematerial.presentations.ui.components.TopBar
import com.binus.online.composematerial.presentations.ui.navigation.NavigationItem
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddScreen(navController: NavController?) {
    val context = LocalContext.current
    val db = DBHandler(context)

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(VictimModel.Gender.LAKI) }
    var age by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(VictimModel.Status.REAKTIF) }
    var desc by remember { mutableStateOf("") }

    var genderExpanded by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Tambah Data Korban",
                onBackButtonClicked = {
                    navController?.popBackStack()
                }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = genderExpanded,
                        onExpandedChange = { genderExpanded = !genderExpanded }
                    ) {
                        OutlinedTextField(
                            value = gender.value,
                            onValueChange = {},
                            label = { Text("Jenis Kelamin") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = genderExpanded,
                            onDismissRequest = { genderExpanded = false }
                        ) {
                            VictimModel.Gender.entries.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.value) },
                                    onClick = {
                                        gender = selectionOption
                                        genderExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Umur") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = statusExpanded,
                        onExpandedChange = { statusExpanded = !statusExpanded }
                    ) {
                        OutlinedTextField(
                            value = status.value,
                            onValueChange = {},
                            label = { Text("Status") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { statusExpanded = false }
                        ) {
                            VictimModel.Status.entries.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.value) },
                                    onClick = {
                                        status = selectionOption
                                        statusExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Deskripsi") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        maxLines = 10, // Allows the field to expand up to 10 lines
                    )

                    Button(
                        onClick = {
                            if (db.insertVictim(
                                name, VictimModel.Gender.valueOf(gender.name), age.toInt(), VictimModel.Status.valueOf(status.name), desc
                            )) {
                                showDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit")
                    }
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                            navController?.navigate(NavigationItem.Home.route) {
                                popUpTo(NavigationItem.AddData.route) { inclusive = true }
                            }
                        },
                        title = { Text("Berhasil") },
                        text = { Text("Data korban berhasil ditambahkan") },
                        confirmButton = {
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colorResource(id = R.color.teal_200), RoundedCornerShape(8.dp)),
                                onClick = {
                                    navController?.navigate(NavigationItem.Home.route)
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
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ComposeMaterialTheme {
        AddScreen(null)
    }
}
