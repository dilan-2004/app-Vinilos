package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.viewmodel.ApiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(
    viewModel: ApiViewModel,
    onBack: () -> Unit
) {
    val vinyls by viewModel.vinyls.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var editingVinyl by remember { mutableStateOf<Vinyl?>(null) }


    LaunchedEffect(Unit) {
        viewModel.loadVinyls()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API REST") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Volver") }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            editingVinyl?.let { vinyl ->
                EditVinylScreen(
                    viewModel = viewModel,
                    vinylId = vinyl.id,
                    onBack = { editingVinyl = null }
                )
                return@Column
            }


            if (loading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
            }

            error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    viewModel.createVinyl(
                        name = "Nuevo Vinilo",
                        artist = "Artista X",
                        albumArt = "https://example.com/imagen.png",
                        price = 20000.0,
                        description = "Descripción del nuevo disco"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Vinilo")
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(vinyls) { vinyl ->
                    VinylCard(
                        vinyl = vinyl,
                        onEdit = {
                            editingVinyl = vinyl
                        },
                        onDelete = { viewModel.deleteVinyl(vinyl.id) }
                    )
                }
            }
        }
    }
}




@Composable
fun VinylCard(
    vinyl: Vinyl,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(vinyl.name, style = MaterialTheme.typography.titleMedium)
            Text("Artista: ${vinyl.artist}")
            Text("Precio: $${vinyl.price}")
            Text("Descripcion: ${vinyl.description}")

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onEdit) {
                    Text("Editar")
                }

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}





