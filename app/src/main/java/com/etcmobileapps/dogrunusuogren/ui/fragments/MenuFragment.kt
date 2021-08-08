package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.databinding.FragmentMenuBinding

private var _binding: FragmentMenuBinding? = null
private val  binding get() = _binding!!

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentMenuBinding.inflate(inflater,container,false)




        binding.playButton.setOnClickListener {
        findNavController().navigate(R.id.action_menuFragment_to_singlePlayerFragment)
        }

        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}