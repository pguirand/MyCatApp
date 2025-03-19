package com.example.mycatapp.data.models


import com.google.gson.annotations.SerializedName

data class CatsItemModel(
    @SerializedName("breeds")
    val breeds: List<BreedModel?>?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)