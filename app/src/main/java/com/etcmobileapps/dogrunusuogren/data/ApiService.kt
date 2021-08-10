package com.etcmobileapps.dogrunusuogren.data

import Question
import com.etcmobileapps.dogrunusuogren.model.Score
import com.etcmobileapps.dogrunusuogren.model.UpdateScore
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("kelimeler.php")
    fun getQuestions():Call<List<Question>>


    @GET("skorlar.php?")
    fun getScoreboard():Call<List<Score>>

    @GET()
    fun setScore(@Url url : String): Call<List<UpdateScore>> //

    @GET()
    fun setUserName(@Url url : String): Call<List<UpdateScore>> //
}