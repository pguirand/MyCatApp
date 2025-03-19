package com.example.mycatapp.data.repository

import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : CatsRepository {
    override suspend fun getCats(breeds: Int,limit: Int): Response<List<CatsItemModel>> {
        return service.getCats(breeds, limit)
    }

}