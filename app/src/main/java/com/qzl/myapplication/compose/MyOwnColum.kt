package com.qzl.myapplication.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MyOwnColum(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) {measurables, constraints ->
        // 全部测量
        val placeables = measurables.map {
            it.measure(constraints)
        }
        var yPosition = 0

        // 布局的大小
        layout(constraints.maxWidth, constraints.maxHeight){
            placeables.forEach{ placeable ->
                // 设置元素的位置
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyOwnColumSample(){
    MyApplicationTheme {
        MyOwnColum(Modifier.padding(8.dp)) {
            Text(text = "MyOwnColum1")
            Text(text = "MyOwnColum2")
            Text(text = "MyOwnColum3")
            Text(text = "MyOwnColum4")
        }
    }
}

@Preview
@Composable
fun PreMyOwnColumSample(){
    MyOwnColumSample()
}