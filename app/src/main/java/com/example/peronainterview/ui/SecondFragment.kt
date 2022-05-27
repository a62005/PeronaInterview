package com.example.peronainterview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peronainterview.databinding.FragmentSecondBinding

class SecondFragment: Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSecondBinding.inflate(inflater)

        SecondFragmentArgs.fromBundle(requireArguments()).item?.let { item ->
            binding.itemText.tvStartTime.text = item.startTime
            binding.itemText.tvEndTime.text = item.endTime
            binding.itemText.tvTemperature.text = item.temperature
        }

        binding.boxLeftTop.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}