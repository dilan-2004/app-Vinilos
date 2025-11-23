package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    vinyl: Vinyl,
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(vinyl.name) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = vinyl.albumArt,
                contentDescription = vinyl.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(vinyl.name, style = MaterialTheme.typography.titleLarge)
            Text("Artista: ${vinyl.artist}", style = MaterialTheme.typography.bodyLarge)
            Text("Precio: $${vinyl.price}", style = MaterialTheme.typography.bodyLarge)
            Text(vinyl.description, style = MaterialTheme.typography.bodyMedium)


            Button(
                onClick = { cartViewModel.addToCart(vinyl) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
