package com.etcmobileapps.dogrunusuogren.ui.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSingleplayerfinishBinding
import com.etcmobileapps.dogrunusuogren.model.Score
import com.etcmobileapps.dogrunusuogren.model.UpdateScore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt


private var _binding: FragmentSingleplayerfinishBinding? = null
private val  binding get() = _binding!!


class SinglePlayerResultFragment : Fragment() {
    val args : SinglePlayerResultFragmentArgs by navArgs()
    var trueValue: String? = null
    var userName: String ? = null
    var currentGold: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSingleplayerfinishBinding.inflate(inflater,container,false)



        binding.scoreValueTv.text = args.scoreValueArg
        binding.trueValue.text = args.trueValueArg
        binding.falseValue.text = args.falseValueArg


        setOnclick()
        getAndSetPrefences()


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

    fun setOnclick () {



        binding.restartButton.setOnClickListener {
            findNavController().navigate(R.id.action_singlePlayerResultFragment_to_singlePlayerFragment)

        }


        binding.mainMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_singlePlayerResultFragment_to_menuFragment)

        }


    }


    fun getAndSetPrefences() {

        val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
        val editor = prefences.edit()

        currentGold = prefences.getInt("KEY_SCORE",100)
        userName = prefences.getString("KEY_USERNAME",null)
        currentGold = currentGold!! + parseInt(args.scoreValueArg)
        editor.putInt("KEY_SCORE", currentGold!!)
        editor.apply()
        UpdateScoreOnScoreboard()

    }
    fun UpdateScoreOnScoreboard() {




        ApiClient.getApiService().setScore("skorkaydet.php?name=" + userName + "&score=" + parseInt(args.scoreValueArg)).enqueue(object : Callback<List<UpdateScore>> {


            override fun onFailure(call: Call<List<UpdateScore>>, t: Throwable) {
            //    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()

                Log.e("Hata",t.toString())

            }

            override fun onResponse(call: Call<List<UpdateScore>>, response: Response<List<UpdateScore>>) {
                var value = response.body()

                if (response.isSuccessful) {
                    Toast.makeText(context, "Yüksek skorlar listesine bakmayı unutma :)", Toast.LENGTH_SHORT).show()

                  //  Toast.makeText(context, userName.toString() + " " + currentGold, Toast.LENGTH_SHORT).show()
                }
            }


        })

    }



}

