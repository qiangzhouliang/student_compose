package com.qzl.myapplication.compose.sideEffects

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.qzl.myapplication.ui.theme.MyApplicationTheme

class SideEffectActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                //LaunchedEffectSample()

                //RememberCoroutineScopeSample()

                //RememberUpdatedStateSample()

                //DisposableEffectSample(onBackPressedDispatcher)

                //produceStateSample()

                //DerivedStateOfSimple()

                SnapshotFlowSample()
            }
        }
    }


}
