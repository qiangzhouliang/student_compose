package com.qzl.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.compose.StaggeredGridBodyContent
import com.qzl.myapplication.data.SampleData
import com.qzl.myapplication.ui.theme.MyApplicationTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                //// A surface container using the 'background' color from the theme
                //Surface(
                //    modifier = Modifier.fillMaxSize(),
                //    color = MaterialTheme.colorScheme.background
                //) {
                //    //MessageCard(message = Message("张三", "我 18 岁了"))
                //    Conversation(messages = SampleData.conversationSample)
                //}

                //PhotographerCard()

                //LayoutStudy()

                //SimpleLists()
                //ScrollingLists()

                //TextWithPaddingToBaseline()

                StaggeredGridBodyContent()
            }

        }
    }

    @Composable
    fun MessageCard(message: Message){
        Row(modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(40.dp)
                .clip(CircleShape))
            Spacer(modifier = Modifier.width(8.dp))

            val isExpanded = remember { mutableStateOf(false) }
            //、设置渐变色
            val surfaceColor: Color by animateColorAsState(
                targetValue = if (isExpanded.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)

            Column(modifier = Modifier.clickable {
                isExpanded.value = !isExpanded.value
            }) {
                Text(text = message.author,
                color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium, // 圆角
                    shadowElevation = 1.dp,  // 阴影
                    color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)) {
                    Text(text = message.body,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = if (isExpanded.value) Int.MAX_VALUE else 1)
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>){
        LazyColumn{
            items(messages){ message ->
                MessageCard(message = message)
            }
        }
    }

    @Preview
    @Composable
    fun PreMessageCard(){
        MyApplicationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                //MessageCard(message = Message("张三", "我 18 岁了"))
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }
}



data class Message(val author: String, val body: String)
