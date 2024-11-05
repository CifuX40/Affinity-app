package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.*

@Composable
fun EnviarVideoAndroid(onBack: () -> Unit) {
    var videoUri by remember { mutableStateOf<android.net.Uri?>(null) }
    val context = androidx.compose.ui.platform.LocalContext.current

    // ActivityResultLauncher para seleccionar video
    val videoPickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) {
            videoUri = uri
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Pantalla de Envío de Video para Android", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para seleccionar video
            Button(onClick = {
                videoPickerLauncher.launch("video/*")
            }) {
                Text("Seleccionar Video")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el video seleccionado si hay uno
            videoUri?.let {
                VideoPlayer(videoUri!!)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}

@Composable
fun VideoPlayer(videoUri: android.net.Uri) {
    // Reproducción de video utilizando VideoView
    androidx.compose.ui.viewinterop.AndroidView(
        factory = { context ->
            android.widget.VideoView(context).apply {
                setVideoURI(videoUri)
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true // Repetir video
                    start() // Iniciar reproducción
                }
            }
        },
        update = { videoView ->
            videoView.setVideoURI(videoUri)
        }
    )
}