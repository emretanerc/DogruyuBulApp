package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentProfileBinding
import com.etcmobileapps.dogrunusuogren.databinding.FragmentScoreboardBinding
import com.etcmobileapps.dogrunusuogren.model.Score
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var _binding: FragmentScoreboardBinding? = null
private val  binding get() = _binding!!

class ScoreboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)


        getScores()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

                var arrayAdapter = ArrayAdapter<string>(this, R.layout.simple_list_item_1, Score)

                binding.scoreListView.adapter = arrayAdapter

                binding.scoreListView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->



                Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()



            }


        })

    }
}