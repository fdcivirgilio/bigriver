package com.example.bigriver.ui.home

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bigriver.R
import androidx.fragment.app.Fragment
import androidx.transition.Visibility

class HeaderAdapter(
    private val fragment: Fragment,
    private val onPostClick: (String) -> Unit,
) :
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

        holder.etMessage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Hide RecyclerView
                val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView?.visibility = View.GONE

                // Show the FrameLayout
                val flNewPost = fragment.view?.findViewById<FrameLayout>(R.id.fl_new_post)
                flNewPost?.let { container ->
                    container.removeAllViews()
                    container.visibility = View.VISIBLE

                    // Inflate item_post.xml and add it to fl_new_post
                    val itemPostView = fragment.layoutInflater.inflate(R.layout.item_header, container, false)
                    container.addView(itemPostView)

                    val btnCancel = itemPostView.findViewById<Button>(R.id.cancelPost)
                    btnCancel?.visibility = View.VISIBLE
                    btnCancel.paintFlags = btnCancel.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                    btnCancel?.setOnClickListener { button ->
                        recyclerView?.visibility = View.VISIBLE
                        button.visibility = View.GONE
                        container.visibility = View.GONE
                        holder.etMessage.clearFocus()

                    }

                    var message: String = ""
                    val btnSend = container.findViewById<Button>(R.id.btn_post_now)
                    btnSend?.setOnClickListener {
                        message = container.findViewById<EditText>(R.id.et_message)?.text.toString().trim()
                        Log.d("HeaderAdapter", "message is: $message")
                        if (message.isNotEmpty()) {
                            onPostClick(message)
                            holder.etMessage.text.clear()
                            container.visibility = View.GONE
                            holder.etMessage.clearFocus()
                            container.removeAllViews()
                            recyclerView?.visibility = View.VISIBLE

                        } else {
                            Toast.makeText(
                                holder.itemView.context,
                                "Message cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = 1 // Always one header row
}