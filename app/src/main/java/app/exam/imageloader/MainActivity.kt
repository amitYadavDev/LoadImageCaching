package app.exam.imageloader

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.exam.imageloader.ui.theme.ImageLoaderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageLoaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    val url = "https://i.pinimg.com/originals/93/09/77/930977991c52b48e664c059990dea125.jpg"

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val imageLoader = remember { ImageLoader(context) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    // Start loading the image
    LaunchedEffect(url) {
        coroutineScope.launch(Dispatchers.IO) {
            val job = launch {
                bitmap = imageLoader.loadImage(url)
            }
            job.join()
            launch {
                bitmap = imageLoader.loadImage(url)
            }
            launch {
                bitmap = imageLoader.loadImage(url)
            }
            launch {
                bitmap = imageLoader.loadImage(url)
            }
            launch {
                bitmap = imageLoader.loadImage(url)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )


        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )


        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )



        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )


        ImageLoaderView(
            bitmap = bitmap,
            modifier = Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )


    }
}
