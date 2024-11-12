package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.jetbrains.compose.resources.*
import java.io.File

// Marca la clase Medidas como serializable
@Serializable
data class Medidas(val centimetros: Int, val kilogramos: Int, val tension: Int)

// Nombre del archivo JSON
const val ARCHIVO_MEDIDAS = "Modo_pruebas.json" // Renombré la constante para evitar conflictos

// Función para guardar las medidas en el archivo JSON
fun guardarMedidas(medidas: Medidas) {
    if (esAndroidDetectado()) {
        mostrarNotificacionWindows("Esta función solo está disponible en Windows.")
        return
    }
    val jsonMedidas = Json.encodeToString(medidas)
    val archivo = File(ARCHIVO_MEDIDAS) // Uso de la nueva constante para evitar conflicto
    archivo.writeText(jsonMedidas)
}

// Función para cargar las medidas desde el archivo JSON
fun cargarMedidas(): Medidas? {
    if (esAndroidDetectado()) {
        mostrarNotificacionWindows("Esta función solo está disponible en Windows.")
        return null
    }
    return try {
        val archivo = File(ARCHIVO_MEDIDAS) // Uso de la nueva constante para evitar conflicto
        if (archivo.exists()) {  // Verifico si el archivo existe
            val jsonMedidas = archivo.readText() // Se utiliza el File para leer el contenido
            Json.decodeFromString<Medidas>(jsonMedidas)
        } else {
            null
        }
    } catch (e: Exception) {
        println("Error al leer el archivo: ${e.message}")
        null
    }
}

// Función para verificar si se está ejecutando en Android
fun esAndroidDetectado(): Boolean {
    return try {
        Class.forName("android.os.Build")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

// Función para mostrar una notificación en consola
fun mostrarNotificacionWindows(mensaje: String) {
    println(mensaje)
}

// Composable que representa la pantalla de pruebas
@Composable
fun Modo_Pruebas(onAceptarClick: () -> Unit) {
    var centimetros by remember { mutableStateOf("") }
    var kilogramos by remember { mutableStateOf("") } // Cambié gramos a kilogramos
    var tension by remember { mutableStateOf("") }
    var medidasGuardadas by remember { mutableStateOf<Medidas?>(null) }

    // Cargar los datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        medidasGuardadas = cargarMedidas()
        medidasGuardadas?.let {
            centimetros = it.centimetros.toString()
            kilogramos = it.kilogramos.toString() // Ahora se carga kilogramos
            tension = it.tension.toString()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
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
            Text(
                text = "Modo Pruebas",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            medidasGuardadas?.let { medidas ->
                Text(text = "Centímetros: ${medidas.centimetros}", fontSize = 20.sp)
                Text(
                    text = "Kilogramos: ${medidas.kilogramos}",
                    fontSize = 20.sp
                ) // Mostramos kilogramos
                Text(text = "Tensión: ${medidas.tension}", fontSize = 20.sp)
            } ?: Text(
                text = "No hay medidas disponibles",
                color = Color.Cyan,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = centimetros,
                onValueChange = { if (it.all { char -> char.isDigit() }) centimetros = it },
                label = { Text("Centímetros") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = kilogramos, // Usamos kilogramos en lugar de gramos
                onValueChange = { if (it.all { char -> char.isDigit() }) kilogramos = it },
                label = { Text("Kilogramos") }, // Actualizamos la etiqueta
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = tension,
                onValueChange = { if (it.all { char -> char.isDigit() }) tension = it },
                label = { Text("Tensión") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (centimetros.isNotEmpty() && kilogramos.isNotEmpty() && tension.isNotEmpty()) {
                        val medidas = Medidas(
                            centimetros = centimetros.toInt(),
                            kilogramos = kilogramos.toInt(), // Cambié gramos a kilogramos
                            tension = tension.toInt()
                        )
                        medidasGuardadas = medidas
                        guardarMedidas(medidas)
                        mostrarNotificacionWindows("Medidas guardadas exitosamente.")
                    } else {
                        mostrarNotificacionWindows("Por favor, completa todos los campos.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAceptarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Regresar")
            }
        }
    }
}