package com.example.vinilos.data.repository

import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.data.remote.ApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VinylRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiService
    private lateinit var repository: VinylRepositoryForTest

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        repository = VinylRepositoryForTest(api)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    class VinylRepositoryForTest(private val testApi: ApiService) {
        suspend fun getProducts() = testApi.getProducts()
        suspend fun getProduct(id: Int) = testApi.getProduct(id)
        suspend fun createProduct(v: Vinyl) = testApi.createProduct(v)
        suspend fun updateProduct(id: Int, v: Vinyl) = testApi.updateProduct(id, v)
        suspend fun deleteProduct(id: Int) = testApi.deleteProduct(id)
    }

    @Test
    fun `getProducts should return list of vinyls`() = kotlinx.coroutines.test.runTest {
        val mockJson = """
            [
                {
                    "id": 1,
                    "name": "Thriller",
                    "artist": "Michael Jackson",
                    "albumArt": "url",
                    "price": 20000.0,
                    "description": "Classic"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val result = repository.getProducts()

        assertEquals(1, result.size)
        assertEquals("Thriller", result[0].name)
    }

    @Test
    fun `getProduct should return one vinyl`() = kotlinx.coroutines.test.runTest {
        val mockJson = """
            {
                "id": 5,
                "name": "Greatest Hits",
                "artist": "2Pac",
                "albumArt": "url",
                "price": 20000.0,
                "description": "Legendary"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val result = repository.getProduct(5)

        assertEquals(5, result.id)
        assertEquals("2Pac", result.artist)
    }

    @Test
    fun `createProduct should send Vinyl and return response`() = kotlinx.coroutines.test.runTest {
        val vinyl = Vinyl(0, "Nuevo", "Artista X", "URl", 19990.0,"Nuevo disco")

        val mockResponse = """
            {
                "id": 10,
                "name": "Nuevo",
                "artist": "Artista X",
                "albumArt": "url",
                "price": 19990.0,
                "description": "Nuevo disco"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(201))

        val result = repository.createProduct(vinyl)

        assertEquals(10, result.id)
        assertEquals("Nuevo", result.name)
    }

    @Test
    fun `updateProduct should update and return response`() = kotlinx.coroutines.test.runTest {
        val updated = Vinyl(1, "Dynamo Editado", "Soda", "url", 21000.0, "Editado")

        val mockResponse = """
             {
                "id": 1,
                "name": "Dynamo Editado",
                "artist": "Soda",
                "albumArt": "url",
                "price": 21000.0,
                "description": "Editado"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val result = repository.updateProduct(1, updated)

        assertEquals("Dynamo Editado", result.name)
        assertEquals(21000.0, result.price, 0.0)
    }

    @Test
    fun `deleteProduct should return success`() = kotlinx.coroutines.test.runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        val result = repository.deleteProduct(1)

        assertEquals(Unit, result)
    }
}