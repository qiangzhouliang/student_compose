package com.qzl.myapplication.compose.sideEffects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.delay


@Composable
fun LandingScreen(onTimeOut: () -> Unit){

    // 保证要执行时的值为最新值
    val currentOnTimeOut by rememberUpdatedState(onTimeOut)

    LaunchedEffect(Unit){
        println("LandingScreen")
        repeat(10){
            delay(1000L)
            println("delay ${it + 1} s")
        }
        currentOnTimeOut()
    }
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RememberUpdatedStateSample() {
    val onTimeOut1: () -> Unit = { println("loading timeout 1.") }
    val onTimeOut2: () -> Unit = { println("loading timeout 2.") }

    val changeOnTimeOutState = remember { mutableStateOf(onTimeOut1) }

    Column {
        Button(onClick = {
            // 修改要执行的函数
            changeOnTimeOutState.value = if (changeOnTimeOutState.value == onTimeOut1) onTimeOut2 else onTimeOut1
        }) {
            Text(text = "choose onTimeout${if (changeOnTimeOutState.value == onTimeOut1) 1 else 2}")
        }

        LandingScreen(changeOnTimeOutState.value)
    }
}
