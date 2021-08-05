package com.etcmobileapps.dogrunusuogren.data

import android.provider.SyncStateContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    fun getApiService(): ApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://www.etcmobileapps.com/kelimelik/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(ApiService::class.java)
    }
}
