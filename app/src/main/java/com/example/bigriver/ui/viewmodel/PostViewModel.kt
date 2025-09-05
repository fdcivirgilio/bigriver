package com.example.bigriver.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bigriver.data.database.AppDatabase
import com.example.bigriver.data.entity.Post
import com.example.bigriver.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postDao = AppDatabase.getDatabase(application).postDao()
    private val repository = PostRepository(postDao)
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    fun printAllUsers() {
        viewModelScope.launch {
            val posts = repository.getAllPost()
            posts.forEach { println(it) }
        }
    }

    fun getAllPostsAsString(): LiveData<String> {
        val result = MutableLiveData<String>()
        viewModelScope.launch {
            val posts = repository.getAllPost()
            result.postValue(posts.joinToString { it.toString() })
        }
        return result
    }

    fun loadPosts() {
        viewModelScope.launch {
            val data = repository.getAllPost()
            // Sort by date (most recent first)
            val sortedData = data.sortedByDescending { it.date }
            _posts.postValue(sortedData)
        }
    }

    fun addPost(content: String, status: Int, userId: Int) {
        val currentTime = System.currentTimeMillis() // current timestamp in ms
        viewModelScope.launch {
            val post = Post(
                content = content,
                date = currentTime,
                status = status,
                dateLastUpdate = currentTime,
                userId = userId
            )
            repository.insertPost(post)
            loadPosts() // refresh LiveData after adding
        }
    }

    fun updatePost(content: String, id: Int) {
        val currentTime = System.currentTimeMillis() // current timestamp in ms
        viewModelScope.launch {
            // Create a Post object with the same ID
            val post = Post(
                id = id,                 // important: existing post ID
                content = content,
                date = 0L,               // keep original date if you have it, or pass it in
                status = 0,              // existing status or pass it in
                dateLastUpdate = currentTime,
                userId = 0               // existing userId or pass it in
            )

            repository.updatePost(post) // <-- call update method in DAO
            loadPosts()                 // refresh LiveData after updating
        }
    }
}