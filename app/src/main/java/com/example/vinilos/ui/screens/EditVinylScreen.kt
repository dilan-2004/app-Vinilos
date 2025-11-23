package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vinilos.viewmodel.ApiViewModel

@Composable
fun EditVinylScreen(
    viewModel: ApiViewModel,
    vinylId: Int,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var albumArt by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }
    var description by remember { mutableStateOf("") }

    val vinyls by viewModel.vinyls.collectAsState()

    LaunchedEffect(vinylId) {
        val v = vinyls.find { it.id == vinylId }
        v?.let {
            name = it.name
            artist = it.artist
            albumArt = it.albumArt
            price = it.price
            description = it.description
        }
    }

    Column(Modifier.padding(16.dp)) {
        Text("Editar Vinilo", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = artist, onValueChange = { artist = it }, label = { Text("Artista") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = albumArt, onValueChange = { albumArt = it }, label = { Text("URL Imagen") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = price.toString(),
            onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Precio") }
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                viewModel.updateVinyl(vinylId, name, artist, albumArt, price, description)
                onBack()
            }) {
                Text("Guardar")
            }

            Button(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }
}




