package com.etcmobileapps.dogrunusuogren.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.etcmobileapps.dogrunusuogren.R
import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentMenuBinding
import com.etcmobileapps.dogrunusuogren.model.UpdateControl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        catchBackButton()


        return binding.root
    }


    override fun onStart() {
        super.onStart()

        getPrefences()
        updateControl()
        //startShakeAnimation()


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


        binding.mailButton.setOnClickListener {
           sendMail(requireActivity(),"Doğruyu Bul Oyunu Hk.","Buraya mesajınızı yazınız.")
        }


        binding.instagramButton.setOnClickListener {
        openInstagramPage()
        }

    }

    fun startShakeAnimation() {
        binding.logoView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        binding.mailTv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_left))
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



        val rnds = (1..99).random()
        val rnds2 = (1..99).random()
        val rnds3 = (1..99).random()
        val rnds4 = (1..10).random()
        var userName = "anon" + rnds.toString() + rnds2.toString() +rnds3.toString()+rnds4.toString()


        return userName
    }


    fun catchBackButton () {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    val builder = AlertDialog.Builder(context)
                    //set title for alert dialog
                    builder.setTitle("Çıkış")
                    //set message for alert dialog
                    builder.setMessage("Uygulamadan çıkmak istiyor musunuz?")
                    builder.setIcon(R.drawable.logo)

                    //performing positive action
                    builder.setPositiveButton("Evet") { dialogInterface, which ->
                        activity?.finish()
                    }
                    //performing cancel action
                    builder.setNegativeButton("Hayır") { dialogInterface, which ->

                    }

                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }


            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

    }




    fun sendMail(
        activity: Activity,
        subject: String,
        textMessage: String
    ) {


        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "iletisim@etcmobileapps.com")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, textMessage)
        emailIntent.setType("message/rfc822")
        try {
            activity.startActivity(
                Intent.createChooser(
                    emailIntent,
                    "Send email using..."
                )
            )
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                activity,
                "No email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    fun openInstagramPage() {



        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://www.instagram.com/etcmobileapps")
        })

    }


    fun updateControl() {
        ApiClient.getApiService().getLastVersion().enqueue(object : Callback<List<UpdateControl>> {
            override fun onFailure(call: Call<List<UpdateControl>>, t: Throwable) {

                  Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(
                call: Call<List<UpdateControl>>,
                response: Response<List<UpdateControl>>
            ) {

                var value = response.body()

                var lastVersion = value?.get(0)?.version
                var feature = value?.get(0)?.feature



                val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)
                var totalQuestion = prefences.getInt("KEY_TOTALQUESTION",0)

                if (totalQuestion==null) {
                    val editor = prefences.edit()
                    var totalQuestionValue = value?.get(0)?.totalQuestion
                    editor.putInt("KEY_TOTALQUESTION", totalQuestionValue!!)
                    editor.apply()
                }



                if (lastVersion==1)
                {

                } else {
                    showUpdateDialog(feature.toString())
                }



            }

    })

    }



    fun showUpdateDialog  (feature :String) {


                    val builder = AlertDialog.Builder(context)
                    //set title for alert dialog
                    builder.setTitle("Güncelleme")
                    //set message for alert dialog
                    builder.setMessage("Uygulamayı güncellemeniz gerekmektedir. \n \n " + feature)
                    builder.setIcon(R.drawable.logo)

                    //performing positive action
                    builder.setPositiveButton("Güncelle") { dialogInterface, which ->

                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://play.google.com/store/apps/details?id=com.etcmobileapps.dogruyubulkelimeoyunu")
                        })
                        activity?.finish()
                    }
                    //performing cancel action
                    builder.setNegativeButton("Kapat") { dialogInterface, which ->
                        activity?.finish()
                    }

                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }



}