package com.example.bigriver.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bigriver.R
import com.example.bigriver.data.entity.Post

class PostAdapter(
    private var posts: List<Post>,
    private val onItemClick: (Post) -> Unit  // pass the click action

) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvPostId: TextView = itemView.findViewById(R.id.tvPostId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvContent.text = post.content
        holder.tvPostId.text = post.id.toString()
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