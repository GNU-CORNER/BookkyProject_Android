package com.example.bookkyandroid.ui.activity.main



import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.data.model.Book
import com.example.bookkyandroid.databinding.ActivityMainBinding



class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)


    }

}