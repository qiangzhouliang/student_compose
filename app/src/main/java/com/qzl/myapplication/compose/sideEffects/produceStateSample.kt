package com.qzl.myapplication.compose.sideEffects

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.repository.Image
import com.qzl.myapplication.repository.ImageRepository
import com.qzl.myapplication.repository.Result


@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun loadNetworkImage(
    url: String,
    imageRepository: ImageRepository
): State<Result<Image>>{
    return produceState(initialValue = Result.Loading as Result<Image>, url, imageRepository){
        val image = imageRepository.load(url)
        value = if (image == null){
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}

@Composable
fun produceStateSample(){

    val imagesList = listOf<String>(
        "https://img2.baidu.com/it/u=259208724,1389675631&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1683392400&t=285940ba51ed179846c6f79d43e9d8b0",
        "https://img0.baidu.com/it/u=501558154,2179992071&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1683392400&t=e72a0177f8011fd89342fce2889b1fcc",
        "https://故意img2.baidu.com/it/u=3392420875,1412947139&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1683392400&t=07fbcfcf2655a97e4abe21c6fe6a676a"
    )

    var index by remember{ mutableStateOf(0) }
    val imageRepository = ImageRepository(LocalContext.current)
    val result = loadNetworkImage(url = imagesList[index], imageRepository = imageRepository)

    Column {
        Button(onClick = {
            index %= imagesList.size
            if (++index == imagesList.size) index = 0
        }) {
            Text(text = "选择第 $index 张图片")
        }

        // 显示图片
        when(result.value) {
            is Result.Success -> {
                Image(
                    bitmap = (result.value as Result.Success).image.imageBitmap,
                    contentDescription = "image load success"
                )
            }

            is Result.Error -> {
                Image(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "image load error",
                    modifier = Modifier.size(200.dp)
                )
            }
            else -> {
                // 进度条
                CircularProgressIndicator()
            }
        }

    }
}