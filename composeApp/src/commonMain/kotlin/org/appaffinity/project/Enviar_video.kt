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
    // Variables de estado para manejar el nombre del archivo, URI seleccionado y mensajes de envío
    var fileName by remember { mutableStateOf<String?>(null) }
    var selectedVideoUri by remember { mutableStateOf<String?>(null) }
    var sendingMessage by remember { mutableStateOf<String?>(null) }

    // Caja que ocupa todo el tamaño disponible
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo, usando painterResource
        Image(
            painterResource(Res.drawable.fondo_de_pantalla), // Asegúrate de tener este recurso empaquetado correctamente.
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna que organiza los elementos verticalmente
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Recupera archivos de video de los directorios especificados
            val videoFiles = remember {
                val primaryDirectoryPath = "C:\\Users\\Public\\Videos"
                val secondaryDirectoryPath = "/Almacenamiento interno/Download/VideoDownloads"

                // Intenta obtener archivos de video del primer directorio
                val primaryVideos = getVideoFiles(File(primaryDirectoryPath))

                if (primaryVideos.isNotEmpty()) {
                    primaryVideos // Si se encuentran videos, devuelve la lista
                } else {
                    // Si el primer directorio está vacío, intenta con el segundo
                    getVideoFiles(File(secondaryDirectoryPath))
                }
            }

            // Muestra el número de videos encontrados
            if (videoFiles.isNotEmpty()) {
                Text(text = "Videos encontrados: ${videoFiles.size}")
                Spacer(modifier = Modifier.height(16.dp))

                // Botón para seleccionar y reproducir el primer video encontrado
                Button(onClick = {
                    val selectedVideoPath =
                        selectVideoFile(videoFiles.firstOrNull()?.parentFile ?: File(""))
                    if (selectedVideoPath != null) {
                        fileName = File(selectedVideoPath).name
                        selectedVideoUri = selectedVideoPath
                        playVideo(selectedVideoPath) // Reproduce el video seleccionado
                    }
                }) {
                    Text(text = "Seleccionar y reproducir vídeo")
                }
            } else {
                // Muestra una alerta si no se encontraron videos
                showAlert("No se encontraron videos en los directorios especificados.")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el nombre del archivo seleccionado o un mensaje por defecto
            Text(text = fileName ?: "No se ha seleccionado ningún archivo")

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar el video
            Button(onClick = {
                if (selectedVideoUri != null) {
                    sendingMessage = "Enviando video: $fileName..."
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000) // Simula un retraso en el envío
                        sendingMessage = "Video enviado exitosamente!"
                    }
                } else {
                    // Muestra una alerta si no se ha seleccionado un video
                    showAlert("No se ha seleccionado ningún video para enviar.")
                }
            }) {
                Text("Enviar vídeo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el mensaje de envío si existe
            sendingMessage?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver a la pantalla anterior
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}

// Función para obtener archivos de video en un directorio dado
fun getVideoFiles(directory: File): List<File> {
    return if (directory.exists() && directory.isDirectory) {
        // Filtra archivos por extensión de video
        directory.listFiles { file ->
            file.extension in listOf("mp4", "mkv", "avi", "mov")
        }?.toList() ?: emptyList() // Devuelve la lista de archivos o vacía si no hay
    } else {
        emptyList() // Devuelve lista vacía si el directorio no existe
    }
}

// Función para reproducir video, distingue entre Android y escritorio
fun playVideo(videoPath: String) {
    if (isAndroid()) {
        // Implementación para Android (debe ser completada)
    } else {
        // Implementación para escritorio
        playVideoOnDesktop(videoPath)
    }
}

// Mostrar alertas en el escritorio
fun showAlert(message: String) {
    JOptionPane.showMessageDialog(null, message)
}

// Función para seleccionar un archivo de video en el escritorio
fun selectVideoFile(directory: File): String? {
    var videoFile: String? = null
    // Crear un diálogo de archivo para seleccionar video
    val dialog =
        java.awt.FileDialog(java.awt.Frame(), "Seleccionar Video", java.awt.FileDialog.LOAD)
    dialog.directory = directory.absolutePath
    dialog.isVisible = true
    if (dialog.file != null) {
        // Devuelve la ruta del archivo seleccionado
        videoFile = File(dialog.directory, dialog.file).absolutePath
    }
    return videoFile
}

// Detectar si la plataforma es Android
fun isAndroid(): Boolean {
    return try {
        Class.forName("android.content.Context") // Intenta cargar una clase de Android
        true
    } catch (e: ClassNotFoundException) {
        false // Si no se encuentra la clase, no es Android
    }
}

// Implementación para escritorio que reproduce el video
fun playVideoOnDesktop(videoPath: String) {
    try {
        val videoFile = File(videoPath)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(videoFile) // Abre el video en el reproductor predeterminado
        }
    } catch (e: Exception) {
        // Muestra un error si falla al intentar reproducir el video
        JOptionPane.showMessageDialog(null, "Error al intentar reproducir: ${e.message}")
    }
}