package com.example.bigriver.ui.posts

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bigriver.R
import com.example.bigriver.data.entity.Post
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneOffset


class PostAdapter(
    private var posts: List<Post>,
    private val onItemClick: (Post) -> Unit  // pass the click action

) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvPostId: TextView = itemView.findViewById(R.id.tvPostId)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvContent.text = post.content
        holder.tvPostId.text = post.id.toString()

        val dateStr = post.dateLastUpdate
        val instant = Instant.ofEpochMilli(dateStr)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
        val nowMillis = System.currentTimeMillis()
        val seconds = (nowMillis - dateStr) / 1000
        val formatted = formatter.format(instant)

        var time = ""

        if(seconds < 5){
            time = "Just now"
        } else if (seconds < 60){
            time = "$seconds seconds ago"
        } else if(seconds < 3600){
            time = "${seconds/60} minutes ago"
        } else if(seconds < 86400){
            time = "${(seconds/60)/60} hours ago"
        } else {
            time = formatted
        }
        holder.tvTime.text = time
        holder.itemView.setOnClickListener {
            onItemClick(post)
        }
    }

    override fun getItemCount() = posts.size

    fun setData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}