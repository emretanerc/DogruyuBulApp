package com.etcmobileapps.dogrunusuogren.data

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    fun getApiService(): ApiService {

        val gson = GsonBuilder()
            .setLenient()
            .create()


        val retrofitBuilder = Retrofit.Builder()

            .baseUrl("http://www.etcmobileapps.com/kelimelik/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofitBuilder.create(ApiService::class.java)
    }
}
