package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSoloBinding




private var _binding: FragmentSoloBinding? = null
private val  binding get() = _binding!!

class SoloPlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSoloBinding.inflate(inflater,container,false)





        binding.firstLayout.setOnClickListener {
            binding.firstLayout.setBackgroundResource(R.drawable.ic_launcher_background)
        }


        binding.secondLayout.setOnClickListener {
            binding.secondLayout.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}