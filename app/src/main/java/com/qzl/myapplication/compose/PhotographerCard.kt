package com.qzl.myapplication.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.R

@Composable
fun PhotographerCard(modifier: Modifier = Modifier){
    Row(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(color = MaterialTheme.colorScheme.surface)
        .clickable(onClick = {})
        .padding(16.dp)) {
        Surface(modifier = Modifier.size(50.dp), shape = CircleShape, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null)
        }
        Column(modifier = Modifier
            .padding(start = 8.dp)
            .align(Alignment.CenterVertically)) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            // 隐式传参
            CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
                Text(text = "三分钟之前", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
fun PrePhotographerCard(){
    PhotographerCard()
}