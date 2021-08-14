package com.etcmobileapps.dogrunusuogren.data

import Question
import com.etcmobileapps.dogrunusuogren.model.UpdateControl
import com.etcmobileapps.dogrusunuogren.model.Score
import com.etcmobileapps.dogrunusuogren.model.UpdateScore
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("kelimeler.php")
    fun getQuestions():Call<List<Question>>


    @GET("skorlar.php?")
    fun getScoreboard():Call<List<Score>>

    @GET("version.php")
    fun getLastVersion():Call<List<UpdateControl>>

    @GET()
    fun setScore(@Url url : String): Call<List<UpdateScore>> //

    @GET()
    fun setUserName(@Url url : String): Call<List<UpdateScore>> //
}