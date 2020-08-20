package com.cagudeloa.memorygame.sequenceGame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.R
import com.cagudeloa.memorygame.databinding.FragmentSequenceBinding
import kotlinx.android.synthetic.main.fragment_sequence.*

class SequenceFragment : Fragment() {

    private lateinit var binding: FragmentSequenceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sequence, container, false)


        val sequenceGame = SequenceGame(binding)
        sequenceGame.setScores()

        binding.playButton.setOnClickListener {
            sequenceGame.showSquares()
            sequenceGame.hideSquares()
            sequenceGame.setListeners()
        }
        return binding.root
    }
}