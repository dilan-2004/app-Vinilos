package com.example.vinilos.data.repository

import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.data.remote.RetrofitInstance

class VinylRepository {
    suspend fun getProducts() = RetrofitInstance.api.getProducts()

    suspend fun getProduct(id: Int) = RetrofitInstance.api.getProduct(id)
    suspend fun createProduct(v: Vinyl) = RetrofitInstance.api.createProduct(v)
    suspend fun updateProduct(id: Int, v: Vinyl) = RetrofitInstance.api.updateProduct(id, v)
    suspend fun deleteProduct(id: Int) = RetrofitInstance.api.deleteProduct(id)

}