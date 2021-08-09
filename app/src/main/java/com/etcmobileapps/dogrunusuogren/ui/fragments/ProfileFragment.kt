package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.databinding.FragmentProfileBinding


private var _binding: FragmentProfileBinding? = null
private val  binding get() = _binding!!

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentProfileBinding.inflate(inflater, container, false)




        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}