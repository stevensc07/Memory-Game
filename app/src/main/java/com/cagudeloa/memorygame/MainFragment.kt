package com.cagudeloa.memorygame

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cagudeloa.memorygame.database.BestScores
import com.cagudeloa.memorygame.database.BestScoresDB
import com.cagudeloa.memorygame.databinding.FragmentMainBinding
import kotlinx.coroutines.GlobalScope
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
        setHasOptionsMenu(true)
        getScoresFromDB()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear_scores_menu -> {
                eraseValuesFromDB(1)
                eraseValuesFromDB(2)
                binding.invalidateAll()
                binding.scoreView.text = "0"
                Toast.makeText(context, "Both scores cleared", Toast.LENGTH_LONG).show()
            }
            R.id.clear_memory_menu -> {
                eraseValuesFromDB(1)
                getScoresFromDB()
                Toast.makeText(context, "Memory Game score cleared", Toast.LENGTH_LONG).show()
            }
            R.id.clear_sequence_menu -> {
                eraseValuesFromDB(2)
                getScoresFromDB()
                Toast.makeText(context, "Sequence Game score cleared", Toast.LENGTH_LONG).show()
            }
            R.id.share -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_text, binding.scoreView.text.toString()))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireActivity(), "Couldn't share, try later :)", Toast.LENGTH_LONG).show()
        }
    }

    private fun eraseValuesFromDB(id: Int){
        GlobalScope.launch {
            val bestScores = BestScores(id, "0")
            context?.let {
                BestScoresDB(it).getBestScoresDao().addScores(bestScores)
                //Log.v("testing", "To database -> $bestScores")
            }
        }
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

    private fun getScoresFromDB(){
        var bestScore1: BestScores?
        var bestScore2: BestScores?
        var valueFromDB1: Int
        var valueFromDB2: Int
        launch {
            context?.let {
                bestScore1 = BestScoresDB(it).getBestScoresDao().getBestScores(id=1)
                bestScore2 = BestScoresDB(it).getBestScoresDao().getBestScores(id=2)
                valueFromDB1 = if(bestScore1 != null) bestScore1!!.score.toInt() else 0
                valueFromDB2 = if(bestScore2 != null) bestScore2!!.score.toInt() else 0
                binding.invalidateAll()
                binding.scoreView.text = (valueFromDB1 + valueFromDB2).toString()
            }
        }
    }
}
