package com.cagudeloa.memorygame.sequenceGame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cagudeloa.memorygame.BaseFragment
import com.cagudeloa.memorygame.MainActivity
import com.cagudeloa.memorygame.R
import com.cagudeloa.memorygame.database.BestScores
import com.cagudeloa.memorygame.database.BestScoresDB
import com.cagudeloa.memorygame.databinding.FragmentSequenceBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SequenceFragment : BaseFragment() {

    private lateinit var binding: FragmentSequenceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sequence, container, false
        )

        val counter: Long = 1000
        val sequenceGame = SequenceGame(binding, counter, activity as MainActivity)
        sequenceGame.setScores()

        binding.playButton.setOnClickListener {
            sequenceGame.showSquares()
            sequenceGame.hideSquares()
            sequenceGame.setListeners()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var bestScores: BestScores?
        var valueFromDB: String
        launch {
            context?.let {
                bestScores = BestScoresDB(it).getBestScoresDao().getBestScores(id=2)
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
            val bestScores = BestScores(2, score)
            context?.let {
                BestScoresDB(it).getBestScoresDao().addScores(bestScores)
                Log.v("testing", "To database -> $bestScores")
            }
        }
    }
}