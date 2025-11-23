package com.example.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vinilos.data.model.Post
import com.example.vinilos.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ApiViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


}

