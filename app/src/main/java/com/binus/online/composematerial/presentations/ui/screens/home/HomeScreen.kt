package com.binus.online.composematerial.presentations.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.binus.online.composematerial.database.DBHandler
import com.binus.online.composematerial.domain.model.VictimModel
import com.binus.online.composematerial.presentations.ui.navigation.NavigationItem
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme
import com.binus.online.composematerial.presentations.ui.theme.Green
import com.binus.online.composematerial.presentations.ui.theme.LightBlue
import com.binus.online.composematerial.presentations.ui.theme.Red
import com.binus.online.composematerial.presentations.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController?) {
    val context = LocalContext.current
    val db = DBHandler(context)
    val victims = db.getDataVictim()

    var name by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController?.navigate(NavigationItem.AddData.route) },
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.small,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Korban")
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Daftar Korban Covid",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    Column {
                        Row {
                            FilterStatus()
                        }
                        Row {
                            SearchBar(
                                query = name,
                                onQueryChange = { name = it },
                                onSearch = {
                                    name = it
                                    db.getDataVictim(name = it)
                                },
                                active = isSearchActive ,
                                onActiveChange = {isSearchActive = it },
                                placeholder = { Text("Cari Nama") }
                            ) {
                                Text("Nama")
                            }
                        }
                    }
                    if (victims.isNotEmpty()) {
                        LazyColumn {
                            items(victims) { victim ->
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                victim.name,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "Kondisi: ${victim.status.value}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = when (victim.status.name) {
                                                    "REAKTIF" -> Yellow
                                                    "DIRAWAT" -> LightBlue
                                                    "MENINGGAL" -> Red
                                                    "SEMBUH" -> Green
                                                    else -> Color.Gray
                                                }
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                navController?.navigate(
                                                    NavigationItem.Detail.createRoute(
                                                        victim.id.toString()
                                                    )
                                                )
                                            }
                                        ) {
                                            Icon(Icons.Outlined.Info, contentDescription = "Detail")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            text = "Data tidak ditemukan"
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterStatus() {
    var status by remember { mutableStateOf(VictimModel.Status.REAKTIF) }
    var statusExpanded by remember { mutableStateOf(false) }
    
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
}

@Preview
@Composable
fun HomePreview() {
    ComposeMaterialTheme {
        HomeScreen(null)
    }
}