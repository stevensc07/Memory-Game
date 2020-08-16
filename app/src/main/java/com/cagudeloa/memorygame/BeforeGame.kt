package com.cagudeloa.memorygame

import android.content.Context
import android.content.DialogInterface
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.cagudeloa.memorygame.databinding.ActivityMainBinding


class BeforeGame(
        private var binding: ActivityMainBinding,
        private var mainCounter: Long,
        private var context: MainActivity)
{
    private var initialCountDown = mainCounter
    private val score = Score("200", "0")
    private lateinit var countDownTimer: CountDownTimer
    private var imageCounter: Int = 0
    private val countDownInterval: Long = 1000
    private var animalLocation = 0
    private var isFirstImage = true
    private var currentSecondImage = 0
    private var alreadySelected = 0
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

    fun setScores(){
        binding.score = score
    }

    fun showAnimals() {
        imageCounter = 0
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
                // Avoid player can click over images when visible
                for (i in imageResources){
                    i.isClickable = false
                }
            }

            override fun onFinish() {
                val resource = R.drawable.question_mark
                binding.countDownText.text = ""
                for (i in 0..11){
                    imageResources[i].setImageResource(resource)
                }
                // Image clickable back (for the user to actually be able to play)
                for (i in imageResources){
                    i.isClickable = true
                }
            }
        }
        countDownTimer.start()
    }

    fun setListeners(){
        for (item in imageResources){
            item.setOnClickListener {
                imageCounter++
                if (imageCounter<=12){
                    callMe(item)
                    if(imageCounter==12){
                        // Check if player got a highest score than the current one
                        if(binding.scoreText.text.toString().toInt() > binding.highestScoreText.text.toString().toInt()){
                            binding.invalidateAll()
                            binding.highestScoreText.text = binding.scoreText.text
                        }
                        binding.mainButton.visibility = View.VISIBLE
                        
                        // Reduce countDown in one second for each new round, 3000 (3 seconds) minimum
                        if (initialCountDown != 2000.toLong())
                            initialCountDown -= 1000
                    }
                }
            }
        }
    }

    private fun callMe(v: ImageView){
        var image2Position: Int
        if(isFirstImage) {
            isFirstImage = !isFirstImage
            // Set the 'selected' drawable in the chosen imageView slot
            val image1Position = imagePosition(v)
            alreadySelected = image1Position
            // Find tapped1 in listNumbers and its couple (where the other image is)
            image2Position = listNumbers.indexOf(image1Position)
            if (image2Position % 2 == 0) {
                image2Position += 1
            } else {
                image2Position -= 1
            }
            animalLocation = selectedAnimal[(image2Position / 2) * 2 + 1]
            image2Position = listNumbers[image2Position]
            currentSecondImage = image2Position
            ////Log.v("testing", "Tapped image at: $image1Position. Couple image at: $image2Position")
            chooseImageLocation(image1Position, 13)
            imageResources[image1Position-1].isClickable = false
        }else{
            // An image was selected already, verify the chosen image now, is same as previous
            // If so, show the images of that animals
            // Else, fill both imageViews with 'incorrect' drawable
            isFirstImage = !isFirstImage
            val image1Position = imagePosition(v)
            if(image1Position == currentSecondImage){
                binding.invalidateAll()
                // Correct choice, increment current score by 10 points
                binding.scoreText.text = (binding.scoreText.text.toString().toInt()+10).toString()
                chooseImageLocation(image1Position, animalLocation-1)
                chooseImageLocation(alreadySelected, animalLocation-1)
                imageResources[image1Position-1].isClickable = false
            }else{
                if((binding.scoreText.text.toString().toInt() -20) <= 0){
                    gameOverDialog()
                    binding.invalidateAll()
                    binding.scoreText.text = "200"
                    val resource = R.drawable.question_mark
                    for (i in 0..11){
                        imageResources[i].setImageResource(resource)
                    }
                    for (i in imageResources){
                        i.isClickable = false
                    }
                    initialCountDown = mainCounter
                    binding.mainButton.visibility = View.VISIBLE
                }else{
                    binding.invalidateAll()
                    // Incorrect choice, decrease current score by 20 points
                    binding.scoreText.text = (binding.scoreText.text.toString().toInt()-20).toString()
                    chooseImageLocation(image1Position, 14)
                    chooseImageLocation(alreadySelected, 14)
                    imageResources[image1Position-1].isClickable = false
                }
            }
            //Log.v("testing", "Animal at $animalLocation First animal is on $alreadySelected")
        }
    }

    private fun gameOverDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Oh no")
        builder.setMessage("Game Over")
        builder.setPositiveButton("Play again") { _: DialogInterface, _ -> }
        builder.show()
    }
    
    private fun imagePosition(view: ImageView): Int{
        return when (view.id) {
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
        }.toInt()
    }
}