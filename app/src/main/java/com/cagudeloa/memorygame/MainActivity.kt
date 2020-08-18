package com.cagudeloa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.cagudeloa.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    var timeToPlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,  R.layout.activity_main)
        navController = this.findNavController( R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        /*
        val counter:Long = 10000
        val beforeGame = BeforeGame(binding, counter, this)
        beforeGame.setScores()

        binding.mainButton.setOnClickListener {
            binding.mainButton.visibility = View.INVISIBLE
            // Show images and countDown timer
            beforeGame.showAnimals()
            // Hide images after countDown reaches zero
            beforeGame.hideImages()
            timeToPlay = !timeToPlay
            beforeGame.setListeners()
        }
        */
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}