package com.example.podcastplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.podcastplayer.ui.theme.PodcastTheme

data class PodcastItem(
    val audioRes: Int,
    val imageRes: Int,
    val title: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PodcastTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    playAudio(this)
                }
            }
        }
    }
}

@Composable
fun playAudio(context: Context) {
    // List of podcast items
    val podcastList = listOf(
        PodcastItem(R.raw.audio, R.drawable.img, "GaurGopalDas Returns To TRS - Life, Monkhood & Spirituality"),
        PodcastItem(R.raw.audio_1, R.drawable.img_1, "Haunted Houses, Evil Spirits & The Paranormal Explained | Sarbajeet Mohanty"),
        PodcastItem(R.raw.audio_2, R.drawable.img_2, "Kaali Mata ki kahani - Black Magic & Aghoris ft. Dr Vineet Aggarwal"),
        PodcastItem(R.raw.audio_3, R.drawable.img_3, "Tantra Explained Simply | Rajarshi Nandy - Mata, Bhairav & Kamakhya Devi"),
        PodcastItem(R.raw.audio_4, R.drawable.img_4, "Complete Story Of Shri Krishna - Explained In 20 Minutes"),
        PodcastItem(R.raw.audio_5, R.drawable.img_5, "Mahabharat Ki Poori Kahaani - Arjun, Shri Krishna & Yuddh - Ami Ganatra")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Podcast title
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "PODCAST",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFF6a3ef9),
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                style = MaterialTheme.typography.h1,
                letterSpacing = 0.1.em
            )
        }

        // Scrollable content
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {

            // Create PodcastCard for each podcast item in the list
            podcastList.forEach { podcast ->
                PodcastCard(context = context, audioRes = podcast.audioRes, imageRes = podcast.imageRes, title = podcast.title)
            }
        }
    }
}

@Composable
fun PodcastCard(context: Context, audioRes: Int, imageRes: Int, title: String) {
    // Remember MediaPlayer to prevent it from being recreated unnecessarily
    val mediaPlayer = remember { MediaPlayer.create(context, audioRes) }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release() // Clean up MediaPlayer when the card is no longer in use
        }
    }

    Card(
        elevation = 12.dp,
        border = BorderStroke(1.dp, Color.Magenta),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(200.dp)
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            Row() {
                IconButton(onClick = { mediaPlayer.start() }, modifier = Modifier.size(35.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Play"
                    )
                }
                IconButton(onClick = { mediaPlayer.pause() }, modifier = Modifier.size(35.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.pause),
                        contentDescription = "Pause"
                    )
                }
            }
        }
    }
}
