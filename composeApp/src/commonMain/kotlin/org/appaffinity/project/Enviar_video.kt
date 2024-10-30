package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import java.io.File
import javax.swing.JOptionPane

@Composable
fun Enviar_video(onBack: () -> Unit) {
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
                .fillMaxSize() // Cambiar a fillMaxSize para ocupar todo el espacio disponible
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar verticalmente
        ) {
            // Mostrar archivos de video encontrados
            val videoFiles = remember {
                fetchVideoFiles()
            }

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
                textAlign = TextAlign.Center // Asegurar que el texto esté centrado
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
                    textAlign = TextAlign.Center // Asegurar que el texto esté centrado
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text(Localization.getString("volver"))
            }
        }
    }
}

// Función para obtener archivos de video en función de la plataforma
fun fetchVideoFiles(): List<File> {
    val primaryDirectoryPath = getPrimaryDirectoryPath()
    return getVideoFiles(File(primaryDirectoryPath))
}

// Función que devuelve la ruta del directorio primario según la plataforma
fun getPrimaryDirectoryPath(): String {
    return if (isAndroid()) {
        "/Almacenamiento interno/Download/VideoDownloads" // Cambia la ruta para Android
    } else {
        "C:\\Users\\Public\\Videos" // Ruta para escritorio
    }
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

// Función para reproducir video
fun playVideo(videoPath: String) {
    if (isAndroid()) {
        // Implementación para Android (debe ser completada)
    } else {
        playVideoOnDesktop(videoPath)
    }
}

// Mostrar alertas en el escritorio
fun showAlert(message: String) {
    if (!isAndroid()) {
        JOptionPane.showMessageDialog(null, message)
    } else {
        // Implementa la alerta para Android
    }
}

// Función para seleccionar un archivo de video
fun selectVideoFile(videoFiles: List<File>): String? {
    return if (isAndroid()) {
        // Implementación para Android (puedes usar un selector de archivos)
        null
    } else {
        // Crear un diálogo de archivo para seleccionar video
        val dialog =
            java.awt.FileDialog(java.awt.Frame(), "Seleccionar Video", java.awt.FileDialog.LOAD)
        dialog.isVisible = true
        if (dialog.file != null) {
            File(dialog.directory, dialog.file).absolutePath
        } else {
            null
        }
    }
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

// Implementación para escritorio que reproduce el video
fun playVideoOnDesktop(videoPath: String) {
    try {
        val videoFile = File(videoPath)
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(videoFile)
        }
    } catch (e: Exception) {
        showAlert("Error al intentar reproducir: ${e.message}")
    }
}