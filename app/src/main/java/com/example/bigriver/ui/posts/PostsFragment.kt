package com.example.bigriver.ui.posts

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bigriver.R
import com.example.bigriver.databinding.FragmentHomeBinding
import com.example.bigriver.databinding.FragmentPostsBinding
import com.example.bigriver.ui.home.HeaderAdapter
import com.example.bigriver.ui.viewmodel.PostViewModel
import kotlin.getValue
import androidx.recyclerview.widget.ConcatAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostsFragment : Fragment() {

    private lateinit var adapter: PostAdapter
    private val postViewModel: PostViewModel by viewModels()
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostAdapter(emptyList()){
                clickedPost ->
            val name = "Dummy Name"
            val postId = clickedPost.id
            showCustomDialogPostSingle(clickedPost.content, clickedPost.date.toString(), name, postId)
        }
        val headerAdapter = HeaderAdapter { message ->
            postViewModel.addPost(message, 0, 0) // add new post
        }

        val concatAdapter = ConcatAdapter(headerAdapter, adapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = concatAdapter

        postViewModel.loadPosts()

        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.setData(posts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showMessageDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Notice")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss() // closes the dialog
        }
        builder.show()
    }

    fun showCustomDialogPostSingle(message: String, date: String, name: String, postId: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_custom_post_single)
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Set width and height slightly smaller to see the rounded corners
        dialog.window?.setLayout((screenWidth * 0.95).toInt(), (screenHeight * 0.9).toInt())

        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        val btnOk = dialog.findViewById<Button>(R.id.btnClose)
        val btnEdit = dialog.findViewById<Button>(R.id.btnEdit)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val etMessage = dialog.findViewById<EditText>(R.id.editMessage)
        val tvPostId = dialog.findViewById<TextView>(R.id.tvPostIdPostSingle)

        //setting minimum height for tvMesage/et
        val minHeightPx = (screenHeight * 0.70).toInt()
        tvMessage.minHeight = minHeightPx
        etMessage.minHeight = minHeightPx

        tvMessage.text = message
        etMessage.setText(message)
        tvPostId.setText(postId.toString())
        btnOk.setOnClickListener {
            dialog.dismiss()
        }

        //setting Edit/Save buttons

        btnEdit.setOnClickListener {
            tvMessage.visibility = View.GONE
            btnEdit.visibility = View.GONE
            etMessage.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
        }

        btnSave.setOnClickListener {
            tvMessage.visibility = View.VISIBLE
            btnEdit.visibility = View.VISIBLE
            etMessage.visibility = View.GONE
            btnSave.visibility = View.GONE

            tvMessage.text = etMessage.text
            val content = etMessage.text.toString()
            val id = tvPostId.text.toString().toInt()
            postViewModel.updatePost(content, id)
        }


        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}