package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.*
import java.awt.*
import java.io.*
import javax.swing.*

@Composable
fun EnviarVideo(onBack: () -> Unit) {
    var fileName by remember { mutableStateOf<String?>(null) }
    var selectedVideoUri by remember { mutableStateOf<String?>(null) }
    var sendingMessage by remember { mutableStateOf<String?>(null) }

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
            // Mostrar archivos de video encontrados
            val videoFiles = remember { fetchVideoFiles() }

            if (videoFiles.isNotEmpty()) {
                Text(
                    text = Localization.getString(
                        "videos_encontrados",
                        videoFiles.size.toString()
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val selectedVideoPath = selectVideoFile(videoFiles)
                    if (selectedVideoPath != null) {
                        fileName = File(selectedVideoPath).name
                        selectedVideoUri = selectedVideoPath
                        playVideo(selectedVideoPath)
                    }
                }) {
                    Text(text = Localization.getString("seleccionar_y_reproducir_video"))
                }
            } else {
                showAlert(Localization.getString("no_videos_encontrados"))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el nombre del archivo seleccionado
            Text(
                text = fileName ?: Localization.getString("no_archivo_seleccionado"),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar video
            Button(onClick = {
                if (selectedVideoUri != null) {
                    sendingMessage = Localization.getString("enviando_video", fileName ?: "")
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        sendingMessage = Localization.getString("video_enviado_exito")
                    }
                } else {
                    showAlert(Localization.getString("no_video_para_enviar"))
                }
            }) {
                Text(Localization.getString("enviar_video"))
            }

            Spacer(modifier = Modifier.height(16.dp))

            sendingMessage?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text(Localization.getString("volver"))
            }
        }
    }
}

// Función para obtener archivos de video en el directorio de videos públicos de Windows
fun fetchVideoFiles(): List<File> {
    val primaryDirectoryPath = "C:\\Users\\Public\\Videos"
    return getVideoFiles(File(primaryDirectoryPath))
}

// Función para obtener archivos de video en un directorio dado
fun getVideoFiles(directory: File): List<File> {
    return if (directory.exists() && directory.isDirectory) {
        directory.listFiles { file ->
            file.extension in listOf("mp4", "mkv", "avi", "mov")
        }?.toList() ?: emptyList()
    } else {
        emptyList()
    }
}

// Función para reproducir video en Windows
fun playVideo(videoPath: String) {
    playVideoOnDesktop(videoPath)
}

// Mostrar alertas en Windows
fun showAlert(message: String) {
    JOptionPane.showMessageDialog(null, message)
}

// Función para seleccionar un archivo de video
fun selectVideoFile(videoFiles: List<File>): String? {
    // Crear un diálogo de archivo para seleccionar video
    val dialog = FileDialog(Frame(), "Seleccionar Video", FileDialog.LOAD)
    dialog.isVisible = true
    return if (dialog.file != null) {
        File(dialog.directory, dialog.file).absolutePath
    } else {
        null
    }
}

// Implementación para reproducir el video en el escritorio de Windows
fun playVideoOnDesktop(videoPath: String) {
    try {
        val videoFile = File(videoPath)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(videoFile)
        }
    } catch (e: Exception) {
        showAlert("Error al intentar reproducir: ${e.message}")
    }
}