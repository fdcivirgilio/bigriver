package com.example.bigriver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bigriver.data.entity.Post

@Dao
interface PostDao {

    @Insert
    suspend fun insert(post: Post)

    @Update
    suspend fun update(post: Post)

    @Query("SELECT * FROM post_table")
    suspend fun getAll(): List<Post>

    // Delete all users
    @Query("DELETE FROM post_table")
    suspend fun deleteAll()
}