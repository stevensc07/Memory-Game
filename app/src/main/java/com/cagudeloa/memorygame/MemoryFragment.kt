package com.cagudeloa.memorygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.cagudeloa.memorygame.databinding.FragmentMemoryBinding

class MemoryFragment : Fragment() {

    lateinit var binding: FragmentMemoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memory, container, false)
        return binding.root
    }
}