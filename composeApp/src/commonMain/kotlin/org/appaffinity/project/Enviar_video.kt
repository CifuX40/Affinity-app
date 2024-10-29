package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import java.awt.*
import java.io.*
import javax.swing.*

@Composable
fun Enviar_video(onBack: () -> Unit) { // Agregar el parámetro onBack
    var fileName by remember { mutableStateOf<String?>(null) }
    var selectedVideoUri by remember { mutableStateOf<String?>(null) }
    var sendingMessage by remember { mutableStateOf<String?>(null) }

    // Usar un Box para superponer la imagen de fondo y el contenido
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val directoryPath = "C:\\Users\\Public\\Videos"
            val directory = File(directoryPath)

            val videoFiles = remember {
                if (directory.exists() && directory.isDirectory) {
                    directory.listFiles { file ->
                        file.extension in listOf("mp4", "mkv", "avi", "mov")
                    }?.toList() ?: emptyList()
                } else {
                    emptyList()
                }
            }

            if (videoFiles.isNotEmpty()) {
                Text(text = "Videos encontrados: ${videoFiles.size}")
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val selectedVideoPath = selectVideoFile(directory)
                    if (selectedVideoPath != null) {
                        fileName = File(selectedVideoPath).name
                        selectedVideoUri = selectedVideoPath
                        playVideo(selectedVideoPath)
                    }
                }) {
                    Text(text = "Seleccionar y reproducir vídeo")
                }
            } else {
                Text(text = "No se encontraron videos.")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = fileName ?: "No se ha seleccionado ningún archivo")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (selectedVideoUri != null) {
                    sendingMessage = "Enviando video: $fileName..."
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        sendingMessage = "Video enviado exitosamente!"
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "No se ha seleccionado ningún video para enviar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                    )
                }
            }) {
                Text("Enviar vídeo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            sendingMessage?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar a la pantalla anterior
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}

fun selectVideoFile(directory: File): String? {
    var videoFile: String? = null
    val dialog = FileDialog(Frame(), "Seleccionar Video", FileDialog.LOAD)
    dialog.directory = directory.absolutePath
    dialog.isVisible = true
    if (dialog.file != null) {
        videoFile = File(dialog.directory, dialog.file).absolutePath
    }
    return videoFile
}

fun playVideo(videoPath: String) {
    try {
        val videoFile = File(videoPath)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(videoFile)
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(
            null,
            "Error al intentar reproducir: ${e.message}",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }
}
