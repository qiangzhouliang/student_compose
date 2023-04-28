package com.qzl.myapplication.compose

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.ui.theme.MyApplicationTheme


/**
 * 自定义布局
 */
fun Modifier.firstBaseLineToTop(
    firstBaseLineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        // 测量元素
        val placeable = measurable.measure(constraints)
        // 测量之后，获取元素的基线值
        val firstBaseline = placeable[FirstBaseline]
        // 元素新的Y坐标 = 新基线值 - 旧基线值
        val placeableY = firstBaseLineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY


        layout(placeable.width, height){
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun TextWithPaddingToBaseline(){
    MyApplicationTheme {
        Text(text = "Hi there!",
        modifier = Modifier.firstBaseLineToTop(24.dp).background(color = Color.Red))
    }
}

@Preview
@Composable
fun PreTextWithPaddingToBaseline(){
    TextWithPaddingToBaseline()
}