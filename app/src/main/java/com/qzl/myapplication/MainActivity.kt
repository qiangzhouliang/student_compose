package com.qzl.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.qzl.myapplication.router.NavRouter
import com.qzl.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavRouter()
                }
            }
        }
    }
}


@Composable
fun Greeting(navController: NavController,name: String = "world", modifier: Modifier = Modifier) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.clickable(indication = null, interactionSource = remember{
                MutableInteractionSource()
            }) {
                println("我被点击了")
                navController.navigate("secondPage")
            }
        )
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null, modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .clip(shape = RoundedCornerShape(size = 20.dp)))
    }

}

@Composable
fun secondPage(){
    Text(text = "第二个页面")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting(navController = rememberNavController())
    }
}