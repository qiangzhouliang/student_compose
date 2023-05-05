package com.qzl.myapplication.compose.sideEffects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSample(
    state: MutableState<Boolean>,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    if(state.value) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message",
                actionLabel = "Retry message"
            )
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        // 标题栏区
        topBar = {
            TopAppBar(
                title = { Text(text = "脚手架示例") }
            )
        },
        // 屏幕内容区域
        content = {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Button(onClick = {
                    state.value = !state.value
                }) {
                    Text(text = "Error occurs")
                }
            }
        })
}


@Composable
fun LaunchedEffectSample() {
    val state = remember { mutableStateOf(false) }
    ScaffoldSample(state)
}

@Preview
@Composable
fun PreLaunchedEffectSample() {
    val state = remember { mutableStateOf(false) }
    ScaffoldSample(state)
}