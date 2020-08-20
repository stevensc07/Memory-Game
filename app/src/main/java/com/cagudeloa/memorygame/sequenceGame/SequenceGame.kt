package com.cagudeloa.memorygame.sequenceGame

import android.util.Log
import android.widget.TextView
import com.cagudeloa.memorygame.R
import com.cagudeloa.memorygame.databinding.FragmentSequenceBinding
import java.util.*
import kotlin.concurrent.timerTask

class SequenceGame(binding: FragmentSequenceBinding) {

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

    // Here is where some squares will go visible for 1-2 seconds
    fun showSquares(){
        howManySquares++
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
        Timer().schedule(timerTask {
            for (i in viewResources){
                i.setBackgroundResource(R.color.tilesColor)
            }
        }, 1000)
    }

    fun setListeners() {
        for (item in viewResources){
            item.setOnClickListener {
                val numberView = selectedView(item)
                Log.d("testing", "Tapped view: $numberView")
            }
        }
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