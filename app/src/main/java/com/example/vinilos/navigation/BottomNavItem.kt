package com.example.vinilos.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart


sealed class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {

    object Home : BottomNavItem(Routes.HOME, "Inicio", Icons.Default.Home)

    object Products : BottomNavItem(Routes.PRODUCTS, "Productos", Icons.Default.List)
    object Contact : BottomNavItem(Routes.CONTACT, "Contacto", Icons.Default.Info)
    object Profile : BottomNavItem(Routes.PROFILE, "Perfil", Icons.Default.Person)

    object Cart : BottomNavItem(Routes.CART, "Carrito", Icons.Default.ShoppingCart)



}

