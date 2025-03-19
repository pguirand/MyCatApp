package com.example.mycatapp.data.repository

import com.example.mycatapp.data.models.CatsItemModel
import retrofit2.Response

interface CatsRepository {
    suspend fun getCats(
        limit:Int,
        breeds:Int
    ) : Response<List<CatsItemModel>>
}