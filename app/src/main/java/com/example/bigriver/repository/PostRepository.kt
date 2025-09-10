package com.example.bigriver.repository

import com.example.bigriver.data.dao.PostDao
import com.example.bigriver.data.entity.Post

class PostRepository(private val postDao: PostDao) {

    suspend fun insertPost(post: Post) {
        postDao.insert(post)
    }

    suspend fun updatePost(post: Post) {
        postDao.update(post)
    }

    suspend fun getAllPost(): List<Post> {
        return postDao.getAll()
    }

    suspend fun deleteAllPosts() {
        postDao.deleteAll()
    }
}