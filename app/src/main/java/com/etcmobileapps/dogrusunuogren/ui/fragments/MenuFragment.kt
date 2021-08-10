package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
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
        getPrefences()

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


        binding.optionLayout.setOnClickListener {
            //findNavController().navigate(R.id.action_menuFragment_to_profileFragment)
        }

    }

    fun startShakeAnimation() {
       //  binding.playButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        binding.logoView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        //binding.optionLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.moreshake))
       // binding.profileButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    }

    fun getPrefences() {


        val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
        var currentGold = prefences.getInt("KEY_SCORE",100)
        var userName = prefences.getString("KEY_USERNAME",null)

        if (userName==null) {

            val editor = prefences.edit()
            editor.putString("KEY_USERNAME",randomUserName())
            editor.apply()
        }




    }

    fun randomUserName(): String {



        val rnds = (1..999).random()
        var userName = "anon" + rnds.toString() + rnds.toString()

        return userName
    }

}