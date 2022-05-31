package com.example.bookkyandroid.ui.activity.main



import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.config.DBController
import com.example.bookkyandroid.config.FeedReaderContract
import com.example.bookkyandroid.databinding.ActivityMainBinding



class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        val dbHelper = FeedReaderContract.FeedReaderDbHelper(this)
        val dbInstance = DBController.getInstance()
        dbInstance.setter(dbHelper)
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.searchFragment || destination.id == R.id.myInfoFragment || destination.id == R.id.suggestionFragment || destination.id == R.id.communityFragment) {
                binding.bottomNav.visibility = View.VISIBLE

            } else {
                binding.bottomNav.visibility = View.GONE
            }
        }

        fun getHelper(): FeedReaderContract.FeedReaderDbHelper {
            return dbHelper
        }
    }

}