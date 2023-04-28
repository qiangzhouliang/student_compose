package com.qzl.myapplication.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // 通过createRefs创建引用，constraintLayout中的每个元素都需要关联一个引用
        val (button, text) = createRefs()
        Button(
            onClick = { /*TODO*/ },
            // 使用Modifier.constrainAs来提供约束，引用作为它的第一个参数
            // 在lambda表达式中指定约束规则
            modifier = Modifier.constrainAs(button){
                // 使用linkTo指定约束，parent是ConstraintLayout的引用
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button")
        }

        Text(text = "Text", modifier = Modifier.constrainAs(text){
            top.linkTo(button.bottom, margin = 16.dp)
            // 在ConstraintLayout中水平居中
            centerHorizontallyTo(parent)
        })
    }
}


@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()
        Button(
            onClick = { /*TODO*/ },
            // 使用Modifier.constrainAs来提供约束，引用作为它的第一个参数
            // 在lambda表达式中指定约束规则
            modifier = Modifier.constrainAs(button1){
                // 使用linkTo指定约束，parent是ConstraintLayout的引用
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button1")
        }

        Text(text = "Text", modifier = Modifier.constrainAs(text){
            top.linkTo(button1.bottom, margin = 16.dp)
            // 在ConstraintLayout中水平居中
            centerAround(button1.end)
        })

        // 将button1 和 text 组合起来，建立一个屏障（barrier）
        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /*TODO*/ },
            // 使用Modifier.constrainAs来提供约束，引用作为它的第一个参数
            // 在lambda表达式中指定约束规则
            modifier = Modifier.constrainAs(button2){
                // 使用linkTo指定约束，parent是ConstraintLayout的引用
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button1")
        }
    }
}

@Composable
fun LargeLayoutContent() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)

        Text(
            text = "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText",
            Modifier.constrainAs(text){
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}


@Preview
@Composable
fun PreConstraintLayoutContent() {
    LargeLayoutContent()
}