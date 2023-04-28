package com.qzl.myapplication.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.data.SampleData.topics
import kotlin.math.max


@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content){measurables, constraints ->
        // 用于保存每行的宽度值
        val rowWidths = IntArray(rows) { 0 }
        // 用于保存每行的高度值
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            // 测量每一个元素
            val placeable = measurable.measure(constraints = constraints)
            // 计算每一行的宽度与高度
            // 元素下标，假设总共11个元素
            // index：0 1 2 3 4 5 6 7 8 9 10
            // 行数，假设3行
            // rows：3
            // 保存行宽高数字下标值
            // row：0 1 2
            val row = index % rows
            // 一行的宽度等于这一行所有元素宽度之和
            rowWidths[row] += placeable.width
            // 一行的高度等于这一行所有元素高度之和
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        // 计算表格的宽高
        // 表格的宽度，应该是所有行当中最宽的哪一行的宽度
        val width = rowWidths.maxOrNull() ?: constraints.minWidth
        val height = rowHeights.sumOf { it }

        // 设置每一行的Y坐标
        val rowY = IntArray(rows){ 0 }

        // 索引从1开始，因为第一行Y坐标为0，row[0] = 0
        for (i in 1 until rows){
            rowY[i] = rowY[i-1] + rowHeights[i - 1]
        }

        layout(width, height){
            val rowX = IntArray(rows){ 0 }
            // 设置每一个元素的坐标
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(x = rowX[row], y = rowY[row])

                // 第一列，x坐标全部为0，下一列的x坐标要累加上前面元素的宽度
                // 设置下一列的x的坐标
                rowX[row] += placeable.width
            }
        }
    }
}


@Composable
fun Chip(modifier: Modifier = Modifier, text: String){
    // 一个卡片，圆角，里面包含一个row，第一列是box，第二列是文本
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
            ) 
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}


@Composable
fun StaggeredGridBodyContent(modifier: Modifier = Modifier){
    Row(modifier = modifier
        .background(color = Color.LightGray)
        .padding(16.dp)
        .horizontalScroll(rememberScrollState()),
    content = {
        StaggeredGrid(modifier = Modifier) {
            for (topic in topics){
                Chip(modifier = Modifier.padding(8.dp),text = topic)
            }
        }
    })
}


@Preview
@Composable
fun PreStaggeredGrid() {
    StaggeredGridBodyContent()
}