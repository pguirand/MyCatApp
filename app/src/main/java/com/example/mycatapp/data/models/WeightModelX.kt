package com.example.mycatapp.data.models


import com.google.gson.annotations.SerializedName

data class WeightModelX(
    @SerializedName("imperial")
    val imperial: String?,
    @SerializedName("metric")
    val metric: String?
)