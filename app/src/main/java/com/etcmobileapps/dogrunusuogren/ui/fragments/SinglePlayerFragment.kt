package com.etcmobileapps.dogrunusuogren.ui.fragments


import Question
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.etcmobileapps.dogrunusuogren.MainActivity
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSingleplayerBinding

import com.google.gson.Gson
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt


private var _binding: FragmentSingleplayerBinding? = null
private val  binding get() = _binding!!


class SinglePlayerFragment : Fragment() {
    var trueValue: String? = null
    var falseValue: String? = null
    var currentJoker: Int = 2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSingleplayerBinding.inflate(inflater,container,false)


        getQuestion()
        setOnclick()


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    findNavController().navigate(R.id.menuFragment)

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getQuestion() {
        ApiClient.getApiService().getQuestions().enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {



                var value = response.body()
                val rnds = (1..42).random()
                trueValue = value?.get(rnds)?.trueword.toString()
                falseValue = value?.get(rnds)?.falseword.toString()



                resetButtons()
                shuffleWords(trueValue!!,falseValue!!)

            }


        })

    }

    fun clickedUpButton (answer:String) {

        binding.firstLayout.isClickable = false
        binding.secondLayout.isClickable = false

        if(answer.equals(trueValue)) {

            binding.firstLayout.setBackgroundResource(R.drawable.trueicon)
            newQuestionWait(true)

        } else {

            //binding.firstLayout.setBackgroundResource(R.drawable.falseicon)
            //newQuestionWait(false)

            val action = SinglePlayerFragmentDirections.actionSinglePlayerFragmentToSinglePlayerResultFragment(
                binding.scoreValueTv.text.toString(),trueValue.toString(),falseValue.toString())

            Navigation.findNavController(requireView()).navigate(action)

        }



    }


    fun clickedDownButton (answer:String) {

        binding.firstLayout.isClickable = false
        binding.secondLayout.isClickable = false


        if(answer.equals(trueValue)) {

            binding.secondLayout.setBackgroundResource(R.drawable.trueicon)
            newQuestionWait(true)
        } else {


           // binding.secondLayout.setBackgroundResource(R.drawable.falseicon)
           // newQuestionWait(false)


               val action = SinglePlayerFragmentDirections.actionSinglePlayerFragmentToSinglePlayerResultFragment(
                   binding.scoreValueTv.text.toString(),trueValue.toString(),falseValue.toString())

                Navigation.findNavController(requireView()).navigate(action)


        }



    }

    fun setOnclick () {


        binding.jokerValue.text = currentJoker.toString()

        binding.firstLayout.setOnClickListener {
            clickedUpButton(binding.firstWordValue.text.toString())
        }


        binding.secondLayout.setOnClickListener {
            clickedDownButton(binding.secondWordValue.text.toString())
        }

        binding.powerButton.setOnClickListener {
            usePowerButton()
        }
    }

    fun resetButtons() {


        binding.firstLayout.setBackgroundResource(R.drawable.buttonbg)
        binding.secondLayout.setBackgroundResource(R.drawable.buttonbg)



    }

    fun newQuestionWait (result:Boolean) {

        if (result) {
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */



                setScore()
                getQuestion()
            }, 500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */

                getQuestion()
            }, 1000)
        }


    }

    fun shuffleWords(word1: String,word2:String) {


        val rnds = (0..1).random()

        if (rnds==0) {
            binding.secondWordValue.text =(word1)
            binding.firstWordValue.text = word2
        } else {

            binding.secondWordValue.text = (word2)
            binding.firstWordValue.text = (word1)
        }

        binding.firstLayout.isClickable = true
        binding.secondLayout.isClickable = true
    }

    fun usePowerButton() {

    if (currentJoker>0) {

        currentJoker = currentJoker-1

        if (binding.firstWordValue.text.equals(trueValue)) {

            clickedUpButton(binding.firstWordValue.text.toString())

        } else {

            clickedDownButton(binding.secondWordValue.text.toString())

        }

        if (currentJoker==0) {
            binding.jokerLayout.setBackgroundResource(R.drawable.jokeremptyicon)
            binding.jokerValue.text=""
        } else {
            binding.jokerValue.text=currentJoker.toString()
        }


    } else {

        Toast.makeText(context, "Şuan jokeriniz bulunmamaktadır.", Toast.LENGTH_SHORT).show()


    }



    }


    fun setScore() {

        var currentScore = parseInt(binding.scoreValueTv.text.toString())
        var newScore = currentScore + 100
        binding.scoreValueTv.setText(newScore.toString())

    }


}

