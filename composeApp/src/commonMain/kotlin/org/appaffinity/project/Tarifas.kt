package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.jetbrains.compose.resources.*
import java.io.File

// Marca la clase Tarifa como serializable
@Serializable
data class Tarifa(val peso: String, val altura: String, val tension: String)

// Nombre del archivo JSON
const val fileName = "Guardar_tarifas.json"

// Función para guardar la tarifa en el archivo JSON solo en Windows
fun guardarTarifa(tarifa: Tarifa) {
    if (esAndroid()) {
        mostrarNotificacion("Esta función solo está disponible en Windows.")
        return
    }
    val jsonTarifa = Json.encodeToString(tarifa)
    val file = File(fileName) // Ruta relativa para escritorio
    file.writeText(jsonTarifa)
}

// Función para cargar la tarifa desde el archivo JSON solo en Windows
fun cargarTarifa(): Tarifa? {
    if (esAndroid()) {
        mostrarNotificacion("Esta función solo está disponible en Windows.")
        return null
    }
    return try {
        val file = File(fileName)
        if (file.exists()) {
            val jsonTarifa = file.readText()
            Json.decodeFromString<Tarifa>(jsonTarifa)
        } else {
            null
        }
    } catch (e: Exception) {
        println("Error al leer el archivo: ${e.message}")
        null
    }
}

// Función para verificar si se está ejecutando en Android
fun esAndroid(): Boolean {
    return try {
        Class.forName("android.os.Build")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

// Composable que representa la pantalla de tarifas
@Composable
fun TarifaScreen(onAceptarClick: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    var tarifaGuardada by remember { mutableStateOf<Tarifa?>(null) }

    // Cargar los datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        tarifaGuardada = cargarTarifa()
        tarifaGuardada?.let {
            peso = it.peso.replace(" céntimos", "")
            altura = it.altura.replace(" céntimos", "")
            tension = it.tension.replace(" céntimos", "")
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
                text = Localization.getString("tarifas"),
                fontSize = 30.sp,
                color = Negro,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            tarifaGuardada?.let { tarifa ->
                Text(text = "Peso: ${tarifa.peso}", fontSize = 20.sp)
                Text(text = "Altura: ${tarifa.altura}", fontSize = 20.sp)
                Text(text = "Tensión: ${tarifa.tension}", fontSize = 20.sp)
            } ?: Text(
                text = "No hay tarifas disponibles",
                color = AzulCian,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = peso,
                onValueChange = { if (it.all { char -> char.isDigit() }) peso = it },
                label = { Text(Localization.getString("precio_peso")) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = altura,
                onValueChange = { if (it.all { char -> char.isDigit() }) altura = it },
                label = { Text(Localization.getString("precio_altura")) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = tension,
                onValueChange = { if (it.all { char -> char.isDigit() }) tension = it },
                label = { Text(Localization.getString("precio_tension")) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Boton_Naranja(
                onClick = {
                    if (peso.isNotEmpty() && altura.isNotEmpty() && tension.isNotEmpty()) {
                        val tarifa = Tarifa(
                            peso = "${(peso.toInt())} céntimos",
                            altura = "${(altura.toInt())} céntimos",
                            tension = "${(tension.toInt())} céntimos"
                        )
                        tarifaGuardada = tarifa
                        guardarTarifa(tarifa)
                        mostrarNotificacion("Tarifa guardada exitosamente.")
                    } else {
                        mostrarNotificacion("Por favor, completa todos los campos.")
                    }
                },
                text = Localization.getString("calcular")
            )

            Spacer(modifier = Modifier.height(8.dp))

            Boton_Naranja(
                onClick = onAceptarClick,
                text = Localization.getString("regresar")
            )
        }
    }
}

// Función para mostrar una notificación en consola
fun mostrarNotificacion(mensaje: String) {
    println(mensaje)
}