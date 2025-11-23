package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(onBack: () -> Unit) {
     var name by remember { mutableStateOf("") }
     var email by remember { mutableStateOf("") }
     var message by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf(false) }

    var success by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        nameError = name.isBlank()
        emailError = !email.contains("@") || !email.contains(".")
        messageError = message.isBlank()

        return !nameError && !emailError && !messageError

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacto") },
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
            Text("Formulario de Contacto", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = false
                },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError
            )
            if (nameError)
                Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error)


            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError
            )
            if (emailError)
                Text("El email no es válido", color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                    messageError = false
                },
                label = { Text("Mensaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                isError = messageError
            )
            if (messageError)
                Text("El mensaje es obligatorio", color = MaterialTheme.colorScheme.error)

            Button(
                onClick = {
                    if (validate()) {
                        success = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar")
            }

            if (success) {
                Text("Mensaje enviado correctamente", color = MaterialTheme.colorScheme.primary)
            }

        }
    }
}