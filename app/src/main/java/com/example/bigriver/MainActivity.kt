package com.example.bigriver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.bigriver.databinding.ActivityMainBinding
import kotlin.text.contains
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import com.example.bigriver.activities.RegisterActivity
import com.example.bigriver.data.entity.User
import com.example.bigriver.ui.viewmodel.PostViewModel
import com.example.bigriver.ui.viewmodel.UserViewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_posts
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
//
//        if (prefs.contains("user_token")) {
//            // Preference exists
//            val value = prefs.getString("user_token", null)
//            Log.d("HomeFragment", "user_token: $value")
//            userViewModel.updateUserToken(
//                userId = 1,
//                path = value.toString()
//            )
//            userViewModel.loadUserByTokenId(value.toString())
//        } else {
//            // Preference does not exist
//            Log.d("HomeFragment", "Preference not found")
//
//            val newToken = java.util.UUID.randomUUID().toString()
//            prefs.edit() {
//                putString("user_token", newToken)
//            } // saves asynchronously
//
//            userViewModel.updateUserToken(
//                userId = 1,
//                path = newToken
//            )
//            userViewModel.loadUserByTokenId(newToken.toString())
//            Log.d("HomeFragment", "Generated new user_token: $newToken")
//        }

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_settings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}