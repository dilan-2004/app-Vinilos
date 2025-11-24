package com.example.vinilos.viewmodel

import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.data.repository.VinylRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ApiViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: VinylRepository
    private lateinit var viewModel: ApiViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        viewModel = ApiViewModel(repository)

    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadVinyls should update vinyl list when repository returns data`() = runTest {
        val fakeList = listOf(
            Vinyl(1, "A", "Artist A", "URL", 1000.0, "desc"),
            Vinyl(2, "B", "Artist B", "URL", 2000.0, "desc")
        )

        coEvery { repository.getProducts() } returns fakeList

        viewModel.loadVinyls()
        testScheduler.advanceUntilIdle()

        Assertions.assertEquals(fakeList, viewModel.vinyls.value)
        Assertions.assertEquals(false, viewModel.loading.value)
        Assertions.assertEquals(null, viewModel.error.value)
    }

    @Test
    fun `createVinyl should call repository and reload list`() = runTest {
        val newVinyl = Vinyl(0, "Nuevo", "Artista", "Img", 20000.0, "Desc")

        coEvery { repository.createProduct(any()) } returns newVinyl
        coEvery { repository.getProducts() } returns listOf(newVinyl)

        viewModel.createVinyl(
            name = "Nuevo",
            artist = "Artista",
            albumArt = "Img",
            price = 20000.0,
            description = "Desc"
        )
        testScheduler.advanceUntilIdle()

        coVerify { repository.createProduct(any()) }
        Assertions.assertEquals(1, viewModel.vinyls.value.size)
    }

    @Test
    fun `updateVinyl should call repository and reload list`() = runTest {
        val updated = Vinyl(10, "Editado", "Artista X", "Img2", 15000.0, "Mod")

        coEvery { repository.updateProduct(10, any()) } returns updated
        coEvery { repository.getProducts() } returns listOf(updated)

        viewModel.updateVinyl(
            id = 10,
            name = "Editado",
            artist = "Artista X",
            albumArt = "Img2",
            price = 15000.0,
            description = "Mod"
        )
        testScheduler.advanceUntilIdle()

        coVerify { repository.updateProduct(10, any()) }
        Assertions.assertEquals("Editado", viewModel.vinyls.value.first().name)
    }

    @Test
    fun `deleteVinyl should call repository and reload list`() = runTest {
        coEvery { repository.deleteProduct(3) } just Runs
        coEvery { repository.getProducts() } returns emptyList()

        viewModel.deleteVinyl(3)
        testScheduler.advanceUntilIdle()

        coVerify { repository.deleteProduct(3) }
        Assertions.assertTrue(viewModel.vinyls.value.isEmpty())
    }

    @Test
    fun `loadVinyls should set error when repository throws exception`() = runTest {
        coEvery { repository.getProducts() } throws Exception("Network ERROR")

        viewModel.loadVinyls()
        testScheduler.advanceUntilIdle()

        Assertions.assertNotNull(viewModel.error.value)
        Assertions.assertEquals("Error: Network ERROR", viewModel.error.value)
    }
}