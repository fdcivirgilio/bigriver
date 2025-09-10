package com.example.bigriver.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bigriver.MainActivity
import com.example.bigriver.R
import com.example.bigriver.activities.RegisterActivity
import com.example.bigriver.databinding.FragmentSettingsBinding
import com.example.bigriver.databinding.FragmentUsersBinding
import com.example.bigriver.ui.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PICK_IMAGE_REQUEST = 100
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {
                val imageView = view?.findViewById<ImageView>(R.id.iv_preview_image)
                imageView?.setImageURI(uri)

                val btnSaveSelectedImage = view?.findViewById<Button>(R.id.btnSaveSelectedImage)
                val btnUploadImage = view?.findViewById<Button>(R.id.btnUploadImage)
                btnSaveSelectedImage?.visibility = View.VISIBLE
                btnUploadImage?.visibility = View.GONE
                btnSaveSelectedImage?.setOnClickListener { button ->
                    // Save it to internal storage
                    val savedPath = saveImageToInternalStorage(uri)
                    var userId = 0
                    userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
                        userId = user?.id ?: 0
                    }
                    val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                    userViewModel.updateUserImage(userId, savedPath.toString())
                    Toast.makeText(requireContext(), "Image saved at: $savedPath", Toast.LENGTH_LONG).show()

                    btnSaveSelectedImage.visibility = View.GONE
                    btnUploadImage?.visibility = View.VISIBLE
                }


            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        // Find the button inside that layout
        val btnUploadImage = rootView.findViewById<Button>(R.id.btnUploadImage)
        btnUploadImage.setOnClickListener {
            pickImageLauncher.launch("image/*")   // ✅ Use new API
        }

        // Return the inflated layout
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logout button
        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            val prefs = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()

            if (prefs.contains("user_token")) {
                editor.remove("user_token")
                editor.apply() // or editor.commit()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        val context = requireContext()
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName) // internal storage

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath // you can save this in Room
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}