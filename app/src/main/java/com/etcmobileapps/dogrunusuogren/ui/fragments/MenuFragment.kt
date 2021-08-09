package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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



        setOnClick()
        startShakeAnimation()

        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnClick () {

        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_singlePlayerFragment)
        }


        binding.scoreBoardButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_scoreboardFragment)
        }


        binding.profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileFragment)
        }

    }

    fun startShakeAnimation() {
       //  binding.playButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        binding.logoView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        //binding.scoreBoardButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
       // binding.profileButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    }
}