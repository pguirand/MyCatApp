package com.example.mycatapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.utils.ApiResponse
import com.example.mycatapp.presentation.viewmodels.CatsViewModel
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun CatScreen(
    viewModel: CatsViewModel = hiltViewModel(),
    navController: NavController
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
                    modifier = Modifier.align(Alignment.Center),
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
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(catsList) { cat ->
                    CatItem(cat, navController)
                }
            }
        }
    }
}

@Composable
fun CatItem(cat: CatsItemModel, navController: NavController) {

    val catJson = URLEncoder.encode(Gson().toJson(cat), "UTF-8")
    Card(
        modifier = Modifier.padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { navController.navigate("cat/details/$catJson") },
//                .clickable { navController.navigate("cat/details/${cat.id}") },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cat.breeds?.first()?.name ?: "",
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = cat.breeds?.first()?.origin ?:"",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Image(
                rememberAsyncImagePainter(cat.url),
                contentDescription = cat.breeds?.first()?.description ?: "",
                modifier = Modifier.size(200.dp)
            )

        }
    }
}
