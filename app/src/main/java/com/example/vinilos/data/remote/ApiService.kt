package com.example.vinilos.data.remote

import com.example.vinilos.data.model.Post
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Post

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Unit
}










