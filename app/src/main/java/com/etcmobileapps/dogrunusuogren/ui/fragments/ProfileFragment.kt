package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentProfileBinding
import com.etcmobileapps.dogrunusuogren.model.UpdateScore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var _binding: FragmentProfileBinding? = null
private val  binding get() = _binding!!

class ProfileFragment : Fragment() {
    var currentUserName : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        getSpecs()
        setOnClick()



        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getSpecs() {

        val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
        val editor = prefences.edit()


            currentUserName = prefences.getString("KEY_USERNAME",null)

        binding.userNameTv.setText(currentUserName)


    }

    fun setOnClick() {

        binding.changeButton.setOnClickListener {
            setUserName(binding.userNameTv.text.toString(), currentUserName.toString())
        }


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

                   Toast.makeText(context, "İsim başarıyla değiştirildi", Toast.LENGTH_SHORT).show()

                     val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
                     val editor = prefences.edit()
                     var  userName = prefences.getString("KEY_USERNAME",null)
                     editor.putString("KEY_USERNAME", newUserName)
                     editor.apply()

               } else if (value!![0].status.equals("This username has already been taken.")) {

                   Toast.makeText(context, "İsim kullanılıyor.", Toast.LENGTH_SHORT).show()
               }

            }


        })


    }
}