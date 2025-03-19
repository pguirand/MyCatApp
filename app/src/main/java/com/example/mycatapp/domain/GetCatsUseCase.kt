package com.example.mycatapp.domain

import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.repository.CatsRepository
import com.example.mycatapp.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val repository: CatsRepository
) {
    operator fun invoke(
        hasBreed:Int,
        limit:Int
    ) : Flow<ApiResponse<List<CatsItemModel>>> = flow {

        emit(ApiResponse.Loading)
        val response = repository.getCats(hasBreed, limit)
        if (response.isSuccessful) {
            response.body()?.let { cats ->
                emit(ApiResponse.Success(cats))
            } ?: throw Exception("No cats found")
        } else throw Exception(response.errorBody()?.string())
    }.catch { e ->
        if (e is Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }
}