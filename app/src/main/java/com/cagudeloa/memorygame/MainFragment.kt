package com.cagudeloa.memorygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cagudeloa.memorygame.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var navController: NavController
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
}