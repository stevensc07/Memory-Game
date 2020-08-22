package com.cagudeloa.memorygame.memoryGame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.BaseFragment
import com.cagudeloa.memorygame.MainActivity
import com.cagudeloa.memorygame.R
import com.cagudeloa.memorygame.database.BestScores
import com.cagudeloa.memorygame.database.BestScoresDB
import com.cagudeloa.memorygame.databinding.FragmentMemoryBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MemoryFragment : BaseFragment() {

    private lateinit var binding: FragmentMemoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_memory, container, false
        )

        val counter: Long =
            8000 // initial countDown will be 8 seconds, decreases one second per round
        val memoryGame = MemoryGame(
            binding,
            counter,
            activity as MainActivity
        )
        memoryGame.setScores()
        binding.mainButton.setOnClickListener {
            binding.mainButton.visibility = View.INVISIBLE
            // Show images and countDown timer
            memoryGame.showAnimals()
            // Hide images after countDown reaches zero
            memoryGame.hideImages()
            memoryGame.setListeners()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var bestScores: BestScores?
        var valueFromDB: String
        launch {
            context?.let {
                bestScores = BestScoresDB(it).getBestScoresDao().getBestScores(id=1)
                if(bestScores != null){
                    valueFromDB = bestScores!!.score
                    binding.invalidateAll()
                    binding.highestScoreText.text = valueFromDB
                    Log.v("testing", "From database -> $valueFromDB")
                }else{
                    Log.v("testing", "Database empty")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        GlobalScope.launch {
            val score = binding.highestScoreText.text.toString()
            val bestScores = BestScores(1, score)
            context?.let {
                BestScoresDB(it).getBestScoresDao().addScores(bestScores)
                Log.v("testing", "To database -> $bestScores")
            }
        }
    }
}