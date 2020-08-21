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
        var bestScores: BestScores
        GlobalScope.launch {
            context?.let {
                bestScores = BestScoresDB(it).getBestScoresDao().getBestScores(id=1)
                Log.v("testing coming info", "The data from database-> $bestScores")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        GlobalScope.launch {
            val bestScores = BestScores(1, "200")
            context?.let {
                BestScoresDB(it).getBestScoresDao().addScores(bestScores)
                Log.v("testing entering info", "Info saved -> $bestScores")
            }
        }
    }
}