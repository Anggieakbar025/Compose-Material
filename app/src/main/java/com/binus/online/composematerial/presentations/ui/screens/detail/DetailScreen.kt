package com.binus.online.composematerial.presentations.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.binus.online.composematerial.database.DBHandler
import com.binus.online.composematerial.presentations.ui.components.TopBar
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme
import com.binus.online.composematerial.presentations.ui.theme.Green
import com.binus.online.composematerial.presentations.ui.theme.LightBlue
import com.binus.online.composematerial.presentations.ui.theme.Red
import com.binus.online.composematerial.presentations.ui.theme.Yellow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController?, id: String) {
    val context = LocalContext.current
    val db = DBHandler(context)

    val data = db.getVictimById(id)
    Scaffold(
        topBar = {
            TopBar(
                title = "Detail Korban",
                onBackButtonClicked = {
                    navController?.popBackStack()
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "Nama",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.name,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Text(
                                text = "Jenis Kelamin",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.gender.value,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Text(
                                text = "Umur",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.age.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Text(
                                text = "Kondisi",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.status.value,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = when (data.status.name) {
                                        "REAKTIF" -> Yellow
                                        "DIRAWAT" -> LightBlue
                                        "MENINGGAL" -> Red
                                        "SEMBUH" -> Green
                                        else -> Color.Gray
                                    }
                                )
                            }

                            Text(
                                text = "Ditambahkan pada",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.createdAt,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Text(
                                text = "Deskripsi Pasien",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            if (data != null) {
                                Text(
                                    text = data.desc,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ComposeMaterialTheme {
        DetailScreen(null, "1")
    }
}
