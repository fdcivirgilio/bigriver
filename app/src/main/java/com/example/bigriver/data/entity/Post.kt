package com.example.bigriver.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val date: Long,            // use Long for timestamps
    val status: Int,
    val dateLastUpdate: Long,   // use Long for timestamps
    val userId: Int
)