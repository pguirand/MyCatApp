package com.example.mycatapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.utils.ApiResponse
import com.example.mycatapp.viewmodels.CatsViewModel

@Composable
fun CatScreen(
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel = hiltViewModel()
) {
    val catsState by viewModel.catsState.collectAsState()

    when(catsState) {
        is ApiResponse.Error -> {
            val errorMessage = (catsState as ApiResponse.Error).errorMessage
            Box(
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                Text(
                    text = errorMessage,
                    modifier = modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
        }
        ApiResponse.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Blue
                )
            }
        }
        is ApiResponse.Success -> {
            val catsList = (catsState as ApiResponse.Success).data
            println("Number of cats : ${catsList.size}")
            LazyColumn(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(catsList) { cat ->
                    CatItem(cat)
                }
            }
        }
    }
}

@Composable
fun CatItem(cat: CatsItemModel) {

    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = cat.breeds?.first()?.name?:"",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 6.dp)
        )

        Image(
            rememberAsyncImagePainter(cat.url),
            contentDescription = cat.breeds?.first()?.description?:"",
            modifier = Modifier.size(200.dp)
        )

    }
}
