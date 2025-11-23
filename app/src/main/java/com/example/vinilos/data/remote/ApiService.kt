package com.example.vinilos.data.remote

import com.example.vinilos.data.model.Vinyl
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface ApiService {

    @GET("products")
    suspend fun getProducts(): List<Vinyl>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Vinyl

    @POST("products")
    suspend fun createProduct(@Body product: Vinyl): Vinyl

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body vinyl: Vinyl): Vinyl

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)


}










