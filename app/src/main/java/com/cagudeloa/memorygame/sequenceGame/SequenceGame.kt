package com.cagudeloa.memorygame.sequenceGame

import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.cagudeloa.memorygame.MainActivity
import com.cagudeloa.memorygame.R
import com.cagudeloa.memorygame.Score
import com.cagudeloa.memorygame.databinding.FragmentSequenceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

class SequenceGame(private var binding: FragmentSequenceBinding, private var activity: MainActivity) {

    // View binding for Scores
    private val score = Score("100", "0")

    private var howManySquares = 2
    private var tappedSquares = 0
    private val listNumbers: MutableList<Int> = (0..24).toMutableList()
    private val viewResources: List<TextView> = listOf(
        binding.squareText1, binding.squareText2, binding.squareText3, binding.squareText4, binding.squareText5,
        binding.squareText6, binding.squareText7, binding.squareText8, binding.squareText9, binding.squareText10,
        binding.squareText11, binding.squareText12, binding.squareText13, binding.squareText14, binding.squareText15,
        binding.squareText16, binding.squareText17, binding.squareText18, binding.squareText19, binding.squareText20,
        binding.squareText21, binding.squareText22, binding.squareText23, binding.squareText24, binding.squareText25
    )

    fun setScores(){
        binding.score = score
    }

    // Here is where some squares will go visible for 1-2 seconds
    fun showSquares(){
        howManySquares++
        tappedSquares = 0
        binding.invalidateAll()
        score.squares = "0 of $howManySquares"
        binding.playButton.visibility = View.INVISIBLE
        for (i in viewResources){
            i.setBackgroundResource(R.color.tilesColor)
            i.isClickable = false
        }
        if(howManySquares <= 20){
            listNumbers.shuffle()
            for (i in 0 until howManySquares){
                viewResources[listNumbers[i]].setBackgroundResource(R.color.tileSelectedColor)
            }
        }else{
            // Already 20 items selected and well answered, start again
            howManySquares = 2
            for (i in 0 until howManySquares){
                viewResources[listNumbers[i]].setBackgroundResource(R.color.tileSelectedColor)
            }
        }
    }

    // Here I'll hide the squares after 1-2 seconds
    fun hideSquares(){
        GlobalScope.launch (context = Dispatchers.Main){
            delay(1000L)
            for (i in viewResources){
                i.setBackgroundResource(R.color.tilesColor)
                i.isClickable = true
            }
        }
    }

    fun setListeners() {
        for (item in viewResources){
            item.setOnClickListener {
                if(tappedSquares <= howManySquares){
                    val numberView = selectedView(item)
                    // Make the selected view, unclickable, no matter if correct or wrong choice
                    viewResources[numberView].isClickable = false
                    // Search in the first $howManySquares items of $listNumbers to know if this should have been selected, else, mark with X
                    if(listNumbers.indexOf(numberView) < howManySquares){
                        //Log.v("testing", "good selection, paint as blue")
                        item.setBackgroundResource(R.color.tileSelectedColor)
                        // Update score
                        updateScore(5)
                        tappedSquares++
                        score.squares = "$tappedSquares of $howManySquares"
                    }else{
                        //Bad choice, mark with $numberView X
                        item.setBackgroundResource(R.drawable.x)
                        // Update score, if not leading not 0 or <0 result
                        if((score.currentScore.toInt()-40) > 0){
                            updateScore(-40)
                        }else{
                            // Score reached or less, GAME OVER
                            binding.playButton.visibility = View.VISIBLE
                            gameOverDialog()
                            howManySquares = 1
                            updateSquaresText()
                            for (i in viewResources){
                                i.setBackgroundResource(R.color.tilesColor)
                                i.isClickable = false
                            }
                        }
                    }
                    //Log.v("testing", "Elements: $listNumbers")
                    //Log.v("testing", "Tapped view: $numberView")
                    if(tappedSquares == howManySquares){
                        binding.invalidateAll()
                        score.squares = "Tap button to play"
                        binding.playButton.visibility = View.VISIBLE
                        for (i in viewResources){
                            i.isClickable = false
                        }
                        if(score.currentScore.toInt() > score.highestScore.toInt()){
                            binding.invalidateAll()
                            score.highestScore = score.currentScore
                        }
                        tappedSquares = 0
                        // Make textViews non-clickable to avoid unwanted behaviours
                    }
                }
            }
        }
    }

    private fun updateSquaresText() {
        binding.invalidateAll()
        score.currentScore = "100"
        score.squares = "Tap button to play"
    }

    private fun updateScore(value: Int){
        binding.invalidateAll()
        score.currentScore = (score.currentScore.toInt()+value).toString()
    }

    private fun gameOverDialog(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Oh no")
        builder.setMessage("Game Over")
        builder.setPositiveButton("Play again") { _: DialogInterface, _ -> }
        builder.show()
    }

    private fun selectedView(v: TextView): Int{
        return when(v.id){
            R.id.square_text1 -> 0
            R.id.square_text2 -> 1
            R.id.square_text3 -> 2
            R.id.square_text4 -> 3
            R.id.square_text5 -> 4
            R.id.square_text6 -> 5
            R.id.square_text7 -> 6
            R.id.square_text8 -> 7
            R.id.square_text9 -> 8
            R.id.square_text10 -> 9
            R.id.square_text11 -> 10
            R.id.square_text12 -> 11
            R.id.square_text13 -> 12
            R.id.square_text14 -> 13
            R.id.square_text15 -> 14
            R.id.square_text16 -> 15
            R.id.square_text17 -> 16
            R.id.square_text18 -> 17
            R.id.square_text19 -> 18
            R.id.square_text20 -> 19
            R.id.square_text21 -> 20
            R.id.square_text22 -> 21
            R.id.square_text23 -> 22
            R.id.square_text24 -> 23
            else -> 24
        }
    }

}