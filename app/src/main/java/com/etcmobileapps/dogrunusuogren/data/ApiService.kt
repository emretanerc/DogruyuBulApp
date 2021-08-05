package com.etcmobileapps.dogrunusuogren.data

import Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("kelimeler.php")
    fun getQuestions():Call<List<Question>>

}