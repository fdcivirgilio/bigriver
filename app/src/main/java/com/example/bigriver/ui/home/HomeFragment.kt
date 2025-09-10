package com.example.bigriver.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bigriver.databinding.FragmentHomeBinding
import com.example.bigriver.ui.viewmodel.PostViewModel
import android.content.Intent
import androidx.transition.Visibility
import com.example.bigriver.R
import com.example.bigriver.ui.posts.PostsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val postViewModel: PostViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val postsFragment = PostsFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.home_fragment_container, postsFragment)
            .commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}