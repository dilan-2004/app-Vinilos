package com.example.vinilos.navigation

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val HOME = "home"
    const val PRODUCTS = "products"
    const val CONTACT = "contact"

    const val PROFILE = "profile"

    const val DETAIL = "detail/{id}"

    const val CART = "cart"

    fun detailRoute(id: Int) = "detail/$id"
}