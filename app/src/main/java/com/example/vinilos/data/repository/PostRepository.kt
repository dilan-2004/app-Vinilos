package com.example.vinilos.data.repository

import com.example.vinilos.data.model.Post
import com.example.vinilos.data.remote.RetrofitInstance

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun createPost(post: Post): Post {
        return RetrofitInstance.api.createPost(post)
    }

    suspend fun updatePost(id: Int, post: Post): Post {
        return RetrofitInstance.api.updatePost(id, post)
    }

    suspend fun deletePost(id: Int) {
        RetrofitInstance.api.deletePost(id)
    }

}