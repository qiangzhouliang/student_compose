package com.qzl.myapplication.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutStudy(){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue, titleContentColor = Color.White, actionIconContentColor = Color.White),
                title = {
                    Text(text = "LayoutStudy")
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )

        }
    ){ innerPadding ->
        bodyContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun bodyContent(modifier: Modifier){
    Column(modifier = modifier
        .padding(start = 8.dp)) {
        Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
        // 隐式传参
        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
            Text(text = "三分钟之前", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun PreLayoutStudy(){
    LayoutStudy()
}