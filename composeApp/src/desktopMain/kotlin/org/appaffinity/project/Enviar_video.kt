package org.prueba.videos.multiplataforma

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.*
import java.io.*
import javax.swing.*

@Composable
fun Enviar_video() {
    // Variables de estado para el nombre del archivo y la URI seleccionada
    var fileName by remember { mutableStateOf<String?>(null) }
    var selectedVideoUri by remember { mutableStateOf<String?>(null) }
    var sendingMessage by remember { mutableStateOf<String?>(null) } // Mensaje para simular el envío

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Usamos un Box para centrar todo en la pantalla
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Centra el contenido en el medio
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Define el directorio
                    val directoryPath = "C:\\Users\\Public\\Videos"
                    val directory = File(directoryPath)

                    // Verifica que el directorio existe
                    val videoFiles = remember {
                        if (directory.exists() && directory.isDirectory) {
                            directory.listFiles { file ->
                                file.extension in listOf("mp4", "mkv", "avi", "mov")
                            }?.toList() ?: emptyList()
                        } else {
                            emptyList()
                        }
                    }

                    // Muestra un mensaje dependiendo de si hay archivos de video
                    if (videoFiles.isNotEmpty()) {
                        Text(text = "Videos encontrados: ${videoFiles.size}")
                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón para abrir el explorador de archivos en el directorio seleccionado
                        Button(onClick = {
                            val selectedVideoPath = selectVideoFile(directory)
                            if (selectedVideoPath != null) {
                                fileName = File(selectedVideoPath).name
                                selectedVideoUri = selectedVideoPath

                                // Reproducir el video inmediatamente después de seleccionarlo
                                playVideo(selectedVideoPath)
                            }
                        }) {
                            Text(text = "Seleccionar y reproducir vídeo")
                        }
                    } else {
                        Text(text = "No se encontraron videos.")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar el nombre del archivo seleccionado
                    Text(text = fileName ?: "No se ha seleccionado ningún archivo")

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simular el envío del video
                    Button(onClick = {
                        if (selectedVideoUri != null) {
                            // Aquí simulamos el envío del video
                            sendingMessage = "Enviando video: $fileName..."
                            // Simular un retraso
                            CoroutineScope(Dispatchers.Main).launch {
                                kotlinx.coroutines.delay(2000) // Simulación de un proceso de envío
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

                    // Mostrar mensaje de envío
                    sendingMessage?.let {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

fun selectVideoFile(directory: File): String? {
    var videoFile: String? = null
    // Crear un cuadro de diálogo de archivo
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
        // Intentar abrir el archivo con el reproductor predeterminado
        val videoFile = File(videoPath)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(videoFile)
        }
    } catch (e: Exception) {
        // Manejo de excepciones si no se puede abrir el archivo
        JOptionPane.showMessageDialog(
            null,
            "Error al intentar reproducir: ${e.message}",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }
}
