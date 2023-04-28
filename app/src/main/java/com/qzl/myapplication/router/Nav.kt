package com.qzl.myapplication.router

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qzl.myapplication.Greeting
import com.qzl.myapplication.secondPage

@Composable
fun NavRouter(){
    // 创建NavController
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Greeting"){
        composable("Greeting"){ Greeting(navController = navController) }
        composable("secondPage"){ secondPage() }
    }
}