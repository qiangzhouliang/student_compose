package com.qzl.myapplication.compose.todo.four

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.compose.todo.TodoIcon
import com.qzl.myapplication.compose.todo.TodoItem

/**
 * 当Todo列表中的条目被选中时，会弹出一个输入框，用于编辑选择的todoItem的信息
 * @param item TodoItem 选中的item
 * @param onEditItemChange Function1<TodoItem, Unit> 编辑条目时的回调
 * @param onEditDone Function0<Unit> 编辑完成时的回调
 * @param onRemoveItem Function1<TodoItem, Unit> 删除条目时的回调
 */
@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
){
    TodoItemInput(
        text = item.task,
        onTextChange = { onEditItemChange(item.copy(task = it)) },
        icon = item.icon,
        onIconChange = { onEditItemChange(item.copy(icon = it)) },
        submit = onEditDone,
        iconVisible = true,
        buttonSlot = {
            // 保存和删除两个图标
            Row {
                val shrinkButtons = Modifier.widthIn(20.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                    Text(text = "\uD83D\uDCBE", textAlign = TextAlign.End, modifier = Modifier.width(30.dp))  // Emoji符号，软盘
                }

                TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                    Text(text = "X", textAlign = TextAlign.End, modifier = Modifier.width(30.dp), color = Color.Red)
                }
            }
        }
    )
}


@Composable
fun TodoItemEntryInput(
    onItemComplete: (TodoItem) -> Unit,
){
    // 根据setText会改变mutableStateOf里面的value值
    // 这个函数使用 remember 给自己添加内存，然后在内存中存储一个由mutableStateOf 创建的 mutableState<String>
    // 它是 Compose 的内置类型，提供了一个可观察的状态持有者
    // val （value, setValue）= remember { mutableStateOf("default) }
    // 对value 的任何更改都将自动重新组合读取此状态的任何可组合函数
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }

    // 图标是否可见取决于文本框中是否有文本
    val iconVisible = text.isNotBlank()

    // 点击 add 按钮，提交要做的事情
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }

    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconVisible = iconVisible
    ){
        TodoEditButton(
            onClick = submit,
            text = "Add",
            enable = text.isNotBlank()
        )
    }
}

/**
 * 输入框
 */
@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconVisible: Boolean,
    // 最右侧的图标与按钮，在编辑时，会有删除和保存两个图标，添加时会有一个 add 按钮
    buttonSlot: @Composable () -> Unit
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            // 输入框
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )
            // 按钮
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.align(Alignment.CenterVertically)){ buttonSlot() }
        }

        if (iconVisible){
            AnimatedIconRow(icon = icon, onIconChange = onIconChange, modifier = Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


// 输入框
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
){

    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        maxLines = 1,
        // 配置软键盘
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            // 点击完成之后，隐藏软键盘
            keyboardController?.hide()
        })
    )
}

// 按钮
@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true
){
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier,
        enabled = enable
    ) {
        Text(text = text)
    }
}

// 一行图标，根据文本是否有内容，自动收起和弹出，带动画效果
@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
){

    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }

    Box(
        modifier = modifier.defaultMinSize(minHeight = 16.dp)
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@Composable
fun IconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        TodoIcon.values().forEach { todoIcon ->
            SelectableIconButton(
                icon = todoIcon.imageVector,
                iconContentDescription = todoIcon.contentDescription,
                onIconSelected = { onIconChange(todoIcon) },
                isSelected = ( todoIcon == icon )
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    iconContentDescription: Int,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    // 图标选中和未选中状态，颜色不一样
    val tint = if (isSelected){
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = { onIconSelected() },
        shape = CircleShape,
        modifier = modifier
    ) {
        Column(modifier) {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(id = iconContentDescription)
            )

            if (isSelected){
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(color = tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

// 顶部输入框加上一个灰色背景
@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
){
    // 帧动画的形式展现surface底部的阴影
    val animateElevation by animateDpAsState(if (elevate) 1.dp else 0.dp, TweenSpec(300))
    Surface(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
        // surface 底部有一个小小的阴影
        shadowElevation = animateElevation,
        shape = RectangleShape
    ) {
        Row(modifier = modifier.animateContentSize(animationSpec = TweenSpec(300))) {
            content()
        }
    }
}
