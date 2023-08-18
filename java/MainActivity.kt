package com.zoltanlorinczi.project_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.ActivityMainBinding

class
MainActivity : AppCompatActivity() {

    val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() called!")
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.tasks -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.listFragment)
                }
                                R.id.activities-> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.activityFragment)
                }

                R.id.groups -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.groupListFragment)
                }
                R.id.settings -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                }
                else ->{
                    false
                }
            }
            true

        }


    }


}