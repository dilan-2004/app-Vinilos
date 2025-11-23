package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onViewDetail: (Int) -> Unit,
    onOpenApi: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(text = "Bienvenido a la aplicación de vinilos", style = MaterialTheme.typography.headlineSmall)

            Text(text = "Explora Clasicos, hip hop, rock y mas....", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    AsyncImage(
                        model = "https://www.udiscovermusic.com/wp-content/uploads/2019/05/Eminem-The-Marshall-Mathers-LP.jpg",
                        contentDescription = "The Marshall Mathers LP",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Album Destacado", style = MaterialTheme.typography.titleMedium)
                    Text("Eminem - The Marshall Mathers LP", style = MaterialTheme.typography.bodyMedium)
                    Text("Precio: $20000.0", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Considerado uno de los mejores álbumes de hip hop de todos los tiempos, 'The Marshall Mathers LP' marcó un antes y un después en la carrera de Eminem. Explora sus letras crudas, su estilo único y su impacto en la cultura.",
                    style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { onViewDetail(6) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver Mas")
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { onOpenApi() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Probar Api")
                    }
                }
            }
        }
    }
}
