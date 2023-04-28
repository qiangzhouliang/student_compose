package com.qzl.myapplication.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun Lists() {
    Column {
        repeat(100){
            Text(text = "item #$it")
        }
    }
}


@Composable
fun SimpleLists() {
    val scrollState = rememberScrollState()
    Column (Modifier.verticalScroll(scrollState)){
        repeat(100){
            Text(text = "item #$it")
        }
    }
}


@Composable
fun LazyLists() {
    val listSize = 100
    val scrollState = rememberLazyListState()

    LazyColumn (state = scrollState){
        items(listSize){
            Text(text = "item #$it")
        }
    }
}

@Composable
fun ScrollingLists() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row{
            Button(modifier = Modifier.weight(1f),onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "滚动到顶部")
            }
            Button(modifier = Modifier.weight(1f),onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "滚动到底部")
            }
        }

        LazyColumn (state = scrollState){
            items(listSize){
                ImageListItem(index = it)
            }
        }
    }

}

@Composable
fun ImageListItem(index: Int){
    Row(verticalAlignment = Alignment.CenterVertically) {
        val rememberAsyncImagePainter =
            rememberAsyncImagePainter(model = "https://img1.baidu.com/it/u=3392591833,1640391553&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1682614800&t=c21503cb1c74a524b6abf2f8a997b365")
        Image(painter = rememberAsyncImagePainter, contentDescription = null
            ,modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "item #$index", style = MaterialTheme.typography.titleMedium)
    }
}


@Preview
@Composable
fun PreLists(){
    Lists()
}