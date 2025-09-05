package com.example.bigriver.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bigriver.R

class HeaderAdapter(private val onPostClick: (String) -> Unit) :
    RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val etMessage: EditText = view.findViewById(R.id.et_message)
        val btnPostNow: Button = view.findViewById(R.id.btn_post_now)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.btnPostNow.setOnClickListener {
            val message = holder.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                onPostClick(message)
                holder.etMessage.text.clear()
            } else {
                Toast.makeText(
                    holder.itemView.context,
                    "Message cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = 1 // Always one header row
}