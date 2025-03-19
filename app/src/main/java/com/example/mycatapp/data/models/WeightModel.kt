package com.example.mycatapp.data.models


import com.google.gson.annotations.SerializedName

data class WeightModel(
    @SerializedName("imperial")
    val imperial: String?,
    @SerializedName("metric")
    val metric: String?
)