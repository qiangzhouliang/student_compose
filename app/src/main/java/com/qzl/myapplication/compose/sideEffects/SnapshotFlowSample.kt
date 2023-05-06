package com.qzl.myapplication.compose.sideEffects

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SnapshotFlowSample(){
    val listState = rememberLazyListState()

    LazyColumn(state = listState){
        items(1000){ index ->
            Text(text = "Item $index")
        }
    }

    LaunchedEffect(listState){
        snapshotFlow { listState.firstVisibleItemIndex }
            .filter { it > 20 }
            .distinctUntilChanged()
            .collect{
                println("firstVisibleItemIndex: $it")
            }
    }
}