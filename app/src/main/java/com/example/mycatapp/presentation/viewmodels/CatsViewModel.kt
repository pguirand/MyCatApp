package com.example.mycatapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.utils.ApiResponse
import com.example.mycatapp.domain.GetCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(){
    private val _catsState = MutableStateFlow<ApiResponse<List<CatsItemModel>>>(ApiResponse.Loading)
    val catsState = _catsState.asStateFlow()

    init {
        retrieveCats()
    }

    private fun retrieveCats(
        hasBreed:Int=1,
        limit:Int = 10
    ) {
        viewModelScope.launch(dispatcher) {
            getCatsUseCase(hasBreed, limit).collect {
                _catsState.value = it
            }
        }
    }
}

