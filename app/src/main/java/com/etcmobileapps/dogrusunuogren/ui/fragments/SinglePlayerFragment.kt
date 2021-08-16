package com.etcmobileapps.dogrusunuogren.ui.fragments


import Question
import android.content.Context
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
import com.etcmobileapps.dogrunusuogren.R


import com.etcmobileapps.dogrunusuogren.data.ApiClient
import com.etcmobileapps.dogrunusuogren.databinding.FragmentSingleplayerBinding
import com.etcmobileapps.dogrusunuogren.MainActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt


private var _binding: FragmentSingleplayerBinding? = null
private val  binding get() = _binding!!


class SinglePlayerFragment : Fragment() {
    var trueValue: String? = null
    var falseValue: String? = null
    var currentJoker: Int = 0
    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"
    lateinit var mAdView : AdView
    var level: Int = 0
    var totalQuestion: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate
        _binding = FragmentSingleplayerBinding.inflate(inflater,container,false)




        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    findNavController().navigate(R.id.menuFragment)

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)





        return binding.root
    }


    override fun onStart() {
        super.onStart()
        getPrefences()
        setOnclick()
        //loadAdRewarded()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getQuestion(totalQuestion: Int?) {



        ApiClient.getApiService().getQuestions().enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {

              //  Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
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
                loadBanner()

            }


        })



        if (level%10==0) {

            currentJoker = currentJoker + 1
            binding.jokerValue.text = currentJoker.toString()
            binding.jokerLayout.setBackgroundResource(R.drawable.jokericon)
         //   binding.jokerLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoomin))
         //   binding.powerButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoomin))
        }


    }


    fun getPrefences() {


        val prefences = requireActivity().getSharedPreferences("SCORE", Context.MODE_PRIVATE)

        var totalQuestionValue = prefences.getInt("KEY_TOTALQUESTION",0)

        totalQuestion = totalQuestionValue


        getQuestion(totalQuestion)






    }


    fun clickedUpButton (answer:String) {

        level=level+1

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

        level=level+1

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
                getQuestion(totalQuestion)
            }, 500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */

                getQuestion(totalQuestion)
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

    //    startShakeAnimation()


    if (currentJoker>0) {

        currentJoker = currentJoker-1
        //Toast.makeText(context, currentJoker.toString(), Toast.LENGTH_SHORT).show()

        if (binding.firstWordValue.text.equals(trueValue)) {

            clickedUpButton(binding.firstWordValue.text.toString())

        } else {

            clickedDownButton(binding.secondWordValue.text.toString())

        }


        if (currentJoker==0) {
            binding.jokerLayout.setBackgroundResource(R.drawable.jokeremptyicon)
            //binding.powerButton.setImageDrawable(null)
            //binding.powerButton.setBackgroundResource(R.drawable.watchadicon)
            binding.jokerValue.text=""

        } else {
            binding.jokerValue.text=currentJoker.toString()


        }



    } else {

        Toast.makeText(context, "Şuan jokeriniz bulunmamaktadır.", Toast.LENGTH_SHORT).show()

     //   showAd()


    }



    }


    fun setScore() {

        var currentScore = parseInt(binding.scoreValueTv.text.toString())
        var newScore = currentScore + 100
        binding.scoreValueTv.setText(newScore.toString())

    }



    fun showAd() {


        if (mRewardedAd != null) {
            mRewardedAd?.show(requireActivity(), OnUserEarnedRewardListener() {
                fun onUserEarnedReward(rewardItem: RewardItem) {

                }
            })
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }


    }

    fun loadBanner() {


        MobileAds.initialize(context) {}

        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


    }
    fun loadAdRewarded() {

        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(requireActivity(),"ca-app-pub-4275218970636966/9915542255", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
              //  Log.d(TAG, adError?.message)
                mRewardedAd = null

            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
              //  currentJoker = 1
              //  binding.jokerValue.text="1"
              //  binding.jokerLayout.setBackgroundResource(R.drawable.jokericon)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }
    }

    fun startShakeAnimation() {
        //  binding.playButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    //   binding.jokerLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoomin))
        //binding.optionLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.moreshake))
        // binding.profileButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    }



}

