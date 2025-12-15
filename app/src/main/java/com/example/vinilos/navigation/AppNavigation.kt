package com.example.vinilos.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vinilos.auth.LoginScreen
import com.example.vinilos.ui.screens.ProductScreen
import com.example.vinilos.ui.screens.ContactScreen
import com.example.vinilos.ui.screens.ProfileScreen
import com.example.vinilos.auth.RegisterScreen
import com.example.vinilos.data.model.SampleVinyls
import com.example.vinilos.data.repository.AuthRepository
import com.example.vinilos.ui.screens.ApiScreen
import com.example.vinilos.ui.screens.CartScreen
import com.example.vinilos.ui.screens.HomeScreen
import com.example.vinilos.ui.screens.ProductDetailScreen
import com.example.vinilos.viewmodel.ApiViewModel
import com.example.vinilos.viewmodel.AuthViewModel
import com.example.vinilos.viewmodel.AuthViewModelFactory
import com.example.vinilos.viewmodel.CartViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun AppNavigation() {
    val apiViewModel: ApiViewModel = viewModel()
    val navController = rememberNavController()
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(AuthRepository(context))
    )
    val cartViewModel: CartViewModel = viewModel()

    val startDestination = if (Firebase.auth.currentUser != null) Routes.HOME else Routes.LOGIN

    val noBottomBarRoutes = listOf(Routes.LOGIN, Routes.REGISTER)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute !in noBottomBarRoutes) {
                BottomBar(navController)
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onRegister = { navController.navigate(Routes.REGISTER) }
                )
            }
            composable(Routes.REGISTER) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.HOME) {
                HomeScreen(
                    onViewDetail = { id ->
                        navController.navigate(Routes.detailRoute(id))
                    },
                    onOpenApi = { navController.navigate(Routes.API) }
                )
            }


            composable(Routes.PRODUCTS) {
                ProductScreen(
                    viewModel = apiViewModel,
                    onProductClick = { id ->
                        navController.navigate(Routes.detailRoute(id))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("id")!!
                val vinyl = SampleVinyls.vinyls.find { it.id == id }

                if (vinyl != null) {
                    ProductDetailScreen(
                        vinyl = vinyl,
                        cartViewModel = cartViewModel,
                        onBack = { navController.popBackStack() }

                    )
                } else {
                    Text("No se encontró el producto")
                }
            }
            composable(Routes.CONTACT) {
                ContactScreen(onBack = { navController.popBackStack() })
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    onLogout = {
                        authViewModel.logout {
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.PROFILE) { inclusive = true }
                            }
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.CART) {
                CartScreen(
                    cartViewModel = cartViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.API) {
                ApiScreen(
                    viewModel = apiViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}