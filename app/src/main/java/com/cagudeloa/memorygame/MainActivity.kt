package com.cagudeloa.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,  R.layout.activity_main)

        binding.mainButton.setOnClickListener {
            val play = Play(binding)
            play.showAnimals()
        }
    }
}