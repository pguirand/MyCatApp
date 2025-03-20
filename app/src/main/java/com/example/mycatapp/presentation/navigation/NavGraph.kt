package com.example.mycatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.presentation.ui.CatDetailsScreen
import com.example.mycatapp.presentation.ui.CatScreen
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "cat_list") {
        composable("cat_list") {
            CatScreen(navController = navController)
        }

        //id
//        composable("cat/details/{catId}") { backStackEntry ->
//            val catId = backStackEntry.arguments?.getString("catId") ?:""
//            CatDetailsScreen(catId)
//        }

        //Json
        composable("cat/details/{catJson}") { backStackEntry ->
            val catJson = backStackEntry.arguments?.getString("catJson") ?:""
            val cat = Gson().fromJson(URLDecoder.decode(catJson, "UTF-8"), CatsItemModel::class.java)
            CatDetailsScreen(cat, navController)
        }
    }

}

