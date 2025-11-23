package com.example.vinilos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vinilos.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel, onBack: () -> Unit) {
    val items = cartViewModel.cartItems.collectAsState().value


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
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
                .fillMaxSize()
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(item.vinyl.name)
                            Text("Cantidad: ${item.quantity}")
                            Text("Subtotal: \$${item.vinyl.price * item.quantity}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { cartViewModel.removeFromCart(item.vinyl.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Total: \$${cartViewModel.totalPrice()}", style = MaterialTheme.typography.titleLarge)

            Button(
                onClick = { cartViewModel.clearCart() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comprar")
            }
        }
    }
}