package com.cagudeloa.memorygame

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.cagudeloa.memorygame.databinding.ActivityMainBinding


class BeforeGame(bind: ActivityMainBinding) {
    private var binding: ActivityMainBinding = bind
    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown: Long = 100000
    private val countDownInterval: Long = 1000
    private var animalLocation = 0
    private var isFirstImage = true
    private var mainCounter = 0
    private val listNumbers: MutableList<Int> = (1..12).toMutableList()
    private val selectedAnimal: MutableList<Int> =  (1..13).toMutableList()
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
        R.drawable.white_dog,

        R.drawable.select,      // For another purposes, not displaying as posible animals
        R.drawable.incorrect   // Same as above

    )

    private val imageResources: List<ImageView> = listOf(
        binding.image1, binding.image2, binding.image3, binding.image4, binding.image5, binding.image6,
        binding.image7, binding.image8, binding.image9, binding.image10, binding.image11, binding.image12
    )

    fun showAnimals() {
        listNumbers.shuffle()
        selectedAnimal.shuffle()
        for (i in 1..11 step 2){    // Each of the image places
            chooseImageLocation(listNumbers[i-1], selectedAnimal[i]-1)
            chooseImageLocation(listNumbers[i], selectedAnimal[i]-1)
        }
    }

     private fun chooseImageLocation(index: Int, selectedImage: Int){
        val drawableResource = when(index){
            1 -> imageResources[0]
            2 -> imageResources[1]
            3 -> imageResources[2]
            4 -> imageResources[3]
            5 -> imageResources[4]
            6 -> imageResources[5]
            7 -> imageResources[6]
            8 -> imageResources[7]
            9 -> imageResources[8]
            10 -> imageResources[9]
            11 -> imageResources[10]
            else -> imageResources[11]
        }
        drawableResource.setImageResource(animals[selectedImage])
    }

    fun hideImages(){
        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(p0: Long) {
                val timeLeft = p0/1000+1
                binding.countDownText.text = timeLeft.toString()
                //binding.mainButton.visibility = View.GONE
            }

            override fun onFinish() {
                val resource = R.drawable.question_mark
                binding.countDownText.text = ""
                for (i in 0..11){
                    imageResources[i].setImageResource(resource)
                }
            }
        }
        countDownTimer.start()
    }

    fun setListeners(){
        for (item in imageResources){
            item.setOnClickListener {
                mainCounter++
                if (mainCounter<=12){
                    callMe(item)
                }else{
                    Log.v("testing", "Game ended")
                }
            }
        }
    }

    private fun callMe(v: ImageView){
        //Log.d("testing", "Image position: ${listNumbers.toString()}")
        //Log.d("testing", "Animals: ${selectedAnimal[1].toString()}, ${selectedAnimal[3].toString()}, ${selectedAnimal[5].toString()}, ${selectedAnimal[7].toString()}, ${selectedAnimal[9].toString()}, ${selectedAnimal[11].toString()}")
        val image1Position = when(v.id){
            R.id.image1 -> "1"
            R.id.image2 -> "2"
            R.id.image3 -> "3"
            R.id.image4 -> "4"
            R.id.image5 -> "5"
            R.id.image6 -> "6"
            R.id.image7 -> "7"
            R.id.image8 -> "8"
            R.id.image9 -> "9"
            R.id.image10 -> "10"
            R.id.image11 -> "11"
            else -> "12"
        }
        // Find tapped1 in listNumbers and its couple (where the other image is)
        var image2Position =listNumbers.indexOf(image1Position.toInt())
        if(image2Position % 2 == 0){
            image2Position += 1
        }else{
            image2Position -= 1
        }
        animalLocation = selectedAnimal[(image2Position/2)*2+1]
        Log.v("testing", "Tapped image at: $image1Position. Couple image at: ${listNumbers[image2Position]}")
        Log.v("testing", "Animal is $animalLocation")
    }
}