package com.cagudeloa.memorygame

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cagudeloa.memorygame.databinding.ActivityMainBinding


class BeforeGame(bind: ActivityMainBinding) {
    private var binding: ActivityMainBinding = bind
    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown: Long = 10000
    private val countDownInterval: Long = 1000

    private val listNumbers: MutableList<Int> = (1..12).toMutableList()
    private val animals = listOf<Int>(
        R.drawable.butterfly,
        R.drawable.color_parrot,
        R.drawable.dog,
        R.drawable.elephant,
        R.drawable.iguana,
        R.drawable.jellyfish,
        R.drawable.jiraffe,
        R.drawable.leopard,
        R.drawable.lion,
        R.drawable.parrot,
        R.drawable.rabbit,
        R.drawable.sharks,
        R.drawable.white_dog
    )

    fun showAnimals() {
        listNumbers.shuffle()
        val selectedAnimal: MutableList<Int> =  (1..13).toMutableList()
        selectedAnimal.shuffle()
        for (i in 1..11 step 2){
            chooseImageLocation(listNumbers[i-1], selectedAnimal[i]-1)
            chooseImageLocation(listNumbers[i], selectedAnimal[i]-1)
        }
    }

     private fun chooseImageLocation(index: Int, selectedImage: Int){
        val drawableResource = when(index){
            1 -> binding.image1
            2 -> binding.image2
            3 -> binding.image3
            4 -> binding.image4
            5 -> binding.image5
            6 -> binding.image6
            7 -> binding.image7
            8 -> binding.image8
            9 -> binding.image9
            10 -> binding.image10
            11 -> binding.image11
            else -> binding.image12
        }
        drawableResource.setImageResource(animals[selectedImage])
    }

    fun hideImages(){
        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(p0: Long) {
                val timeLeft = p0/1000+1
                binding.countDownText.text = timeLeft.toString()
            }

            override fun onFinish() {
                val resource = R.drawable.question_mark
                binding.apply {
                    countDownText.text = "0"
                    mainButton.visibility = View.GONE
                    image1.setImageResource(resource)
                    image2.setImageResource(resource)
                    image3.setImageResource(resource)
                    image4.setImageResource(resource)
                    image5.setImageResource(resource)
                    image6.setImageResource(resource)
                    image7.setImageResource(resource)
                    image8.setImageResource(resource)
                    image9.setImageResource(resource)
                    image10.setImageResource(resource)
                    image11.setImageResource(resource)
                    image12.setImageResource(resource)
                }
            }
        }
        countDownTimer.start()
    }
}