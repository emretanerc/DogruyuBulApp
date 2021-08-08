package com.etcmobileapps.dogrunusuogren.ui.fragments

import com.etcmobileapps.dogrunusuogren.databinding.ActivityMainBinding.inflate


import Question
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSingleplayerfinishBinding


private var _binding: FragmentSingleplayerfinishBinding? = null
private val  binding get() = _binding!!


class SinglePlayerResultFragment : Fragment() {
    val args : SinglePlayerResultFragmentArgs by navArgs()
    var trueValue: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSingleplayerfinishBinding.inflate(inflater,container,false)



        binding.scoreValue.text = args.scoreValueArg
        binding.trueValue.text = args.trueValueArg
        binding.falseValue.text = args.falseValueArg

        setOnclick()






        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnclick () {



        binding.restartButton.setOnClickListener {
            findNavController().navigate(R.id.action_singlePlayerResultFragment_to_singlePlayerFragment)

        }


        binding.mainMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_singlePlayerResultFragment_to_menuFragment)

        }


    }




}

