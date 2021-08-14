package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.etcmobileapps.dogrunusuogren.adapters.ScoreboardRowAdapter
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentScoreboardBinding

import com.etcmobileapps.dogrusunuogren.model.Score
import com.etcmobileapps.dogrunusuogren.model.UpdateScore

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var _binding: FragmentScoreboardBinding? = null
private val  binding get() = _binding!!

class ScoreboardFragment : Fragment() {
    private var mAdapter: ScoreboardRowAdapter?= null;
    private var mQuestions: MutableList<Score> = ArrayList()
    var currentUserName : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)

        binding.scoreView!!.layoutManager = LinearLayoutManager(context)

        mAdapter = ScoreboardRowAdapter(requireContext(), mQuestions, R.layout.simple_list_item_1)
        binding.scoreView!!.adapter = mAdapter

        setOnClick()
        getSpecs()
        getScores()
        catchBackButton()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun setOnClick () {


        binding.changeButton.setOnClickListener {

            var newUserName = binding.userNameTv.text.toString()
            if (newUserName.equals("")) {
                Toast.makeText(context, "Kullanıcı adı boş seçilemez.", Toast.LENGTH_SHORT).show()
            } else {
                setUserName(binding.userNameTv.text.toString(), currentUserName.toString())
            }
        }

    }

    fun getScores() {
        ApiClient.getApiService().getScoreboard().enqueue(object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>, t: Throwable) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()

            }


            override fun onResponse(
                call: Call<List<Score>>,
                response: Response<List<Score>>
            ) {


                val scores = response.body()

                if (scores != null) {
                    mQuestions.clear()
                    mQuestions.addAll(scores!!)
                    mAdapter!!.notifyDataSetChanged()
                }


               // Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()



            }


        })

    }

    fun setUserName (newUserName : String, oldUserName : String) {


        ApiClient.getApiService().setUserName("isimdegistir.php?name="+ newUserName + "&oldname=" + oldUserName).enqueue(object : Callback<List<UpdateScore>> {
            override fun onFailure(call: Call<List<UpdateScore>>, t: Throwable) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(
                call: Call<List<UpdateScore>>,
                response: Response<List<UpdateScore>>
            ) {

                var value = response.body()

                if (value!![0].status.equals("Username has been changed.")) {

                    Toast.makeText(context, "Kullanıcı adı başarıyla değiştirildi", Toast.LENGTH_SHORT).show()

                    val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
                    val editor = prefences.edit()
                    var  userName = prefences.getString("KEY_USERNAME",null)
                    editor.putString("KEY_USERNAME", newUserName)
                    editor.apply()
                    getScores()
                    hideKeyboard(requireView())

                } else if (value!![0].status.equals("This username has already been taken.")) {

                    Toast.makeText(context, "Kullanıcı adı kullanılıyor.", Toast.LENGTH_SHORT).show()
                }

            }


        })


    }


    fun getSpecs() {

        val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
        val editor = prefences.edit()


        currentUserName = prefences.getString("KEY_USERNAME",null)

        binding.userNameTv.setText(currentUserName)



    }


    fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun catchBackButton () {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    findNavController().navigate(com.etcmobileapps.dogrunusuogren.R.id.menuFragment)

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

    }
}