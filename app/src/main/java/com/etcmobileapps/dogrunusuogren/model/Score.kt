package com.etcmobileapps.dogrunusuogren.model

import com.google.gson.annotations.SerializedName

data class Score (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("score") val score : Int


)