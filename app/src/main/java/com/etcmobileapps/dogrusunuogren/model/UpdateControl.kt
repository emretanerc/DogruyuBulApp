package com.etcmobileapps.dogrunusuogren.model

import com.google.gson.annotations.SerializedName

data class UpdateControl (

    @SerializedName("version") val version : Int?,
    @SerializedName("feature") val feature : String?,



)