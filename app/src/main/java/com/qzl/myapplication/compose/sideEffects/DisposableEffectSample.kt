package com.qzl.myapplication.compose.sideEffects

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue

@Composable
fun BackHandler(
    backPressedDispatcher: OnBackPressedDispatcher,
    onBack: () -> Unit
){
    val currentObBack by rememberUpdatedState(newValue = onBack)
    val backCallback = remember {
        object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                currentObBack()
            }
        }
    }
    DisposableEffect(backPressedDispatcher){
        backPressedDispatcher.addCallback(backCallback)
        onDispose {
            println("onDispose")
            backCallback.remove()
        }
    }
}

@Composable
fun DisposableEffectSample(backPressedDispatcher: OnBackPressedDispatcher) {
    var addBackCallback by remember { mutableStateOf(false) }


    Row {
        Switch(checked = addBackCallback, onCheckedChange = {
            addBackCallback = !addBackCallback
        })

        Text(text = if (addBackCallback) "Add Back Callback" else "Not Add Back Callback")
    }

    if (addBackCallback){
        BackHandler(backPressedDispatcher){
            println("on back")
        }
    }

}
