package com.cagudeloa.memorygame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cagudeloa.memorygame.database.BestScores
import com.cagudeloa.memorygame.database.BestScoresDB
import com.cagudeloa.memorygame.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : BaseFragment() {

    private lateinit var navController: NavController
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.memoryButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_memoryFragment)
        }
        binding.sequenceButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_sequenceFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var bestScore1: BestScores?
        var bestScore2: BestScores?
        var valueFromDB1: Int
        var valueFromDB2: Int
        launch {
            context?.let {
                bestScore1 = BestScoresDB(it).getBestScoresDao().getBestScores(id=1)
                bestScore2 = BestScoresDB(it).getBestScoresDao().getBestScores(id=2)
                if(bestScore1 != null && bestScore2 != null){
                    valueFromDB1 = bestScore1!!.score.toInt()
                    valueFromDB2 = bestScore2!!.score.toInt()
                    binding.invalidateAll()
                    binding.scoreView.text = (valueFromDB1 + valueFromDB2).toString()
                    //Log.v("testing", "From database -> ${(valueFromDB1 + valueFromDB2)}")
                }else{
                    binding.scoreView.text = "0"
                }
            }
        }
    }
}
