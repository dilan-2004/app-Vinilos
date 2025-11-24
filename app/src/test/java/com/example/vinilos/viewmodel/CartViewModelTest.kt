package com.example.vinilos.viewmodel

import com.example.vinilos.data.model.Vinyl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    private val vinyl1 = Vinyl(
        id = 1,
        name = "Thriller",
        artist = "Michael Jackson",
        albumArt = "img.png",
        price = 20000.0,
        description = "Clásico"
    )

    private val vinyl2 = Vinyl(
        id = 2,
        name = "Back in Black",
        artist = "AC/DC",
        albumArt = "img2.png",
        price = 25000.0,
        description = "Rock legendario"
    )

    @Before
    fun setup() {
        viewModel = CartViewModel()
    }

    @Test
    fun `agregar un vinilo al carrito`() = runTest {
        viewModel.addToCart(vinyl1)

        val result = viewModel.cartItems.value

        result.size shouldBe 1
        result.first().vinyl shouldBe vinyl1
        result.first().quantity shouldBe 1
    }

    @Test
    fun `agregar dos veces el mismo vinilo aumenta la cantidad`() = runTest {
        viewModel.addToCart(vinyl1)
        viewModel.addToCart(vinyl1)

        val item = viewModel.cartItems.value.first()

        item.vinyl shouldBe vinyl1
        item.quantity shouldBe 2
    }

    @Test
    fun `eliminar un vinilo del carrito`() = runTest {
        viewModel.addToCart(vinyl1)
        viewModel.addToCart(vinyl2)

        viewModel.removeFromCart(vinyl1.id)

        val result = viewModel.cartItems.value

        result.map { it.vinyl } shouldContainExactly listOf(vinyl2)
    }

    @Test
    fun `eliminar vinilo que no existe NO cambia el carrito`() = runTest {
        viewModel.addToCart(vinyl1)

        viewModel.removeFromCart(vinyl2.id)

        val result = viewModel.cartItems.value

        result.map { it.vinyl } shouldContainExactly listOf(vinyl1)
    }

    @Test
    fun `calcular total del carrito`() = runTest {
        viewModel.addToCart(vinyl1) // 20000
        viewModel.addToCart(vinyl2) // 25000

        val total = viewModel.totalPrice()

        total shouldBe (20000.0 + 25000.0)
    }

    @Test
    fun `calcular total de carrito vacio`() = runTest {
        val total = viewModel.totalPrice()

        total shouldBe 0.0
    }

    @Test
    fun `limpiar carrito deja lista vacia`() = runTest {
        viewModel.addToCart(vinyl1)
        viewModel.addToCart(vinyl2)

        viewModel.clearCart()

        viewModel.cartItems.value.size shouldBe 0
    }
}

