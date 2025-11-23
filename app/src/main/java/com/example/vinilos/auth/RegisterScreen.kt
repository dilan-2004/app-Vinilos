package com.example.vinilos.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vinilos.viewmodel.AuthViewModel


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    authViewModel: AuthViewModel
) {
    val loading by authViewModel.loading.collectAsState()
    val error by authViewModel.error.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { authViewModel.register(email, password) { onRegisterSuccess() } },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()) {
            if (loading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text("Crear Cuenta")
        }

        error?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}