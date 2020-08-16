package com.cagudeloa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    var timeToPlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,  R.layout.activity_main)

        val counter:Long = 5000
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
    }
}