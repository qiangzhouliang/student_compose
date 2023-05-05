package com.qzl.myapplication.compose.sideEffects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RememberCoroutineScopeSample() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        // 标题栏区
        topBar = {
            TopAppBar(
                title = { Text(text = "脚手架示例") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null, tint = Color.White)
                    }
                }
            )
        },
        // 屏幕内容区域
        content = {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "屏幕内容区域")
            }
        },
        // 悬浮按钮
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "悬浮按钮") },
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = "点击了悬浮按钮")
                    }
                }
            )
        },
        // 左侧抽屉
        drawerContent = {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "抽屉组件中的内容")
            }
        }
    )
}

