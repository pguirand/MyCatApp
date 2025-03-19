package com.example.mycatapp.data.service

import com.example.mycatapp.data.common.ApiConstants
import com.example.mycatapp.data.models.CatsItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.SEARCH_ENDPOINT)
    suspend fun getCats(
        @Query("has_breeds") breeds: Int,
        @Query("limit") limit:Int
    ) : Response<List<CatsItemModel>>
}