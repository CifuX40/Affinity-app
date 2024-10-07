package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PantallaContrasena(onBack: () -> Unit, rutaArchivo: String) {
    var contrasenaActual by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    // Cargar la contraseña actual desde el archivo
    LaunchedEffect(Unit) {
        val contrasena = leerContrasenaDesdeArchivo(rutaArchivo)
        contrasenaActual = contrasena?.valor ?: "No disponible"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Contraseña actual: $contrasenaActual", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nuevaContrasena,
            onValueChange = { nuevaContrasena = it },
            label = { Text("Nueva Contraseña") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nuevaContrasena.isNotEmpty()) {
                    // Lanzar una coroutine para guardar la contraseña
                    CoroutineScope(Dispatchers.Main).launch {
                        guardarContrasenaEnArchivo(rutaArchivo, Contrasena(nuevaContrasena))
                        mostrarError = false
                        onBack()
                    }
                } else {
                    mostrarError = true
                }
            }
        ) {
            Text("Guardar")
        }

        if (mostrarError) {
            Text(text = "La nueva contraseña no puede estar vacía", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}

// Modelo para representar una contraseña
data class Contrasena(val valor: String)

// Función para leer la contraseña desde un archivo JSON
suspend fun leerContrasenaDesdeArchivo(rutaArchivo: String): Contrasena? {
    return withContext(Dispatchers.IO) {
        val archivo = File(rutaArchivo)
        if (archivo.exists()) {
            val contenido = archivo.readText()
            return@withContext Contrasena(contenido)
        }
        return@withContext null
    }
}

// Función para guardar la contraseña en un archivo JSON
suspend fun guardarContrasenaEnArchivo(rutaArchivo: String, contrasena: Contrasena) {
    withContext(Dispatchers.IO) {
        val archivo = File(rutaArchivo)
        archivo.writeText(contrasena.valor)
    }
}
