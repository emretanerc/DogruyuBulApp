package com.etcmobileapps.dogrunusuogren.ui.fragments


import Question
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSoloBinding
import com.google.gson.Gson
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response


private var _binding: FragmentSoloBinding? = null
private val  binding get() = _binding!!


class SoloPlayerFragment : Fragment() {
    var trueValue: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSoloBinding.inflate(inflater,container,false)


        getQuestion()
        setOnclick()





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

                resetButtons()

                val rnds = (1..43).random()

                trueValue = value?.get(rnds)?.trueword

                binding.firstWordValue.setText(value?.get(rnds)?.trueword.toString())
                binding.secondWordValue.setText(value?.get(rnds)?.falseword.toString())

            }


        })

    }

    fun clickedUpButton (answer:String) {

        if(answer.equals(trueValue)) {

            binding.firstLayout.setBackgroundResource(R.drawable.trueicon)
            newQuestionWait(true)

        } else {

            binding.secondLayout.setBackgroundResource(R.drawable.falseicon)
            newQuestionWait(false)
        }



    }


    fun clickedDownButton (answer:String) {

        if(answer.equals(trueValue)) {

            binding.secondLayout.setBackgroundResource(R.drawable.trueicon)
            newQuestionWait(true)
        } else {


            binding.secondLayout.setBackgroundResource(R.drawable.falseicon)
            newQuestionWait(false)
        }



    }

    fun setOnclick () {

        binding.firstLayout.setOnClickListener {
            clickedUpButton(binding.firstWordValue.text.toString())
        }


        binding.secondLayout.setOnClickListener {
            clickedDownButton(binding.secondWordValue.text.toString())
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

                getQuestion()
            }, 500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */

                getQuestion()
            }, 1000)
        }


    }
}

