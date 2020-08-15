package com.cagudeloa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    var timeToPlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,  R.layout.activity_main)

        val beforeGame = BeforeGame(binding)
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