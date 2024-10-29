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
import java.io.File
import javax.swing.JOptionPane
import java.awt.Desktop

@Composable
fun Enviar_video(onBack: () -> Unit) {
    var fileName by remember { mutableStateOf<String?>(null) }
    var selectedVideoUri by remember { mutableStateOf<String?>(null) }
    var sendingMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo, usando painterResource
        Image(
            painterResource(Res.drawable.fondo_de_pantalla), // Asegúrate de tener este recurso empaquetado correctamente.
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val videoFiles = remember {
                val primaryDirectoryPath = "C:\\Users\\Public\\Videos"
                val secondaryDirectoryPath = "/Almacenamiento interno/Download/VideoDownloads"

                // Intenta obtener archivos de video del primer directorio
                val primaryVideos = getVideoFiles(File(primaryDirectoryPath))

                if (primaryVideos.isNotEmpty()) {
                    primaryVideos
                } else {
                    // Si el primer directorio está vacío, intenta con el segundo
                    getVideoFiles(File(secondaryDirectoryPath))
                }
            }

            if (videoFiles.isNotEmpty()) {
                Text(text = "Videos encontrados: ${videoFiles.size}")
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val selectedVideoPath = selectVideoFile(videoFiles.firstOrNull()?.parentFile ?: File(""))
                    if (selectedVideoPath != null) {
                        fileName = File(selectedVideoPath).name
                        selectedVideoUri = selectedVideoPath
                        playVideo(selectedVideoPath)
                    }
                }) {
                    Text(text = "Seleccionar y reproducir vídeo")
                }
            } else {
                showAlert("No se encontraron videos en los directorios especificados.")
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
                    showAlert("No se ha seleccionado ningún video para enviar.")
                }
            }) {
                Text("Enviar vídeo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            sendingMessage?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}

// Función para obtener archivos de video
fun getVideoFiles(directory: File): List<File> {
    return if (directory.exists() && directory.isDirectory) {
        directory.listFiles { file ->
            file.extension in listOf("mp4", "mkv", "avi", "mov")
        }?.toList() ?: emptyList()
    } else {
        emptyList()
    }
}

// Función para reproducir video (distingue entre Android y escritorio)
fun playVideo(videoPath: String) {
    if (isAndroid()) {
        // Implementación para Android
        // Necesitas implementar tu código Android específico aquí
    } else {
        // Implementación para escritorio
        playVideoOnDesktop(videoPath)
    }
}

// Mostrar alertas (implementación para escritorio)
fun showAlert(message: String) {
    JOptionPane.showMessageDialog(null, message)
}

// Función para seleccionar archivo (implementación para escritorio)
fun selectVideoFile(directory: File): String? {
    var videoFile: String? = null
    val dialog = java.awt.FileDialog(java.awt.Frame(), "Seleccionar Video", java.awt.FileDialog.LOAD)
    dialog.directory = directory.absolutePath
    dialog.isVisible = true
    if (dialog.file != null) {
        videoFile = File(dialog.directory, dialog.file).absolutePath
    }
    return videoFile
}

// Detectar si la plataforma es Android
fun isAndroid(): Boolean {
    return try {
        Class.forName("android.content.Context")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

// Implementación para escritorio
fun playVideoOnDesktop(videoPath: String) {
    try {
        val videoFile = File(videoPath)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(videoFile)
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al intentar reproducir: ${e.message}")
    }
}
