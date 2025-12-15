package com.example.vinilos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vinilos.viewmodel.ApiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ApiViewModel,
    onProductClick: (Int) -> Unit,
    onBack: () -> Unit) {

    val vinylList by viewModel.vinyls.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadVinyls()
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos") },
                navigationIcon = {
                    TextButton(onClick = { onBack() }) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding()
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(vinylList) { vinyl ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClick(vinyl.id) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        AsyncImage(
                            model = vinyl.albumArt,
                            contentDescription = "Portada de ${vinyl.name}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )

                        Text(vinyl.name, style = MaterialTheme.typography.titleMedium)
                        Text("Artista: ${vinyl.artist}")
                        Text("Precio: $${vinyl.price}")
                    }
                }
            }
        }
    }
}