package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.jetbrains.compose.resources.*
import java.io.File

@Serializable
data class Tarifa(val peso: String, val altura: String, val tension: String)

const val fileName = "Guardar_tarifas.json"

// Función para guardar la tarifa en el archivo JSON solo en Windows
fun guardarTarifa(tarifa: Tarifa) {
    if (esAndroid()) {
        mostrarNotificacion("Esta función solo está disponible en Windows.")
        return
    }
    val jsonTarifa = Json.encodeToString(tarifa)
    val file = File(fileName)
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

    val keyboardController = LocalSoftwareKeyboardController.current

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
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        // Ocultar el teclado al hacer clic fuera de los campos
                        keyboardController?.hide()
                    })
                },
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

            // Label para mostrar el teclado numérico
            Text(
                text = Localization.getString("precio_peso"),
                modifier = Modifier
                    .clickable {
                        // Mostrar el teclado para 'peso'
                        keyboardController?.show()
                    }
                    .padding(8.dp)
            )

            // Entrada de peso
            TextField(
                value = peso,
                onValueChange = { if (it.all { char -> char.isDigit() }) peso = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Label para mostrar el teclado numérico
            Text(
                text = Localization.getString("precio_altura"),
                modifier = Modifier
                    .clickable {
                        // Mostrar el teclado para 'altura'
                        keyboardController?.show()
                    }
                    .padding(8.dp)
            )

            // Entrada de altura
            TextField(
                value = altura,
                onValueChange = { if (it.all { char -> char.isDigit() }) altura = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Label para mostrar el teclado numérico
            Text(
                text = Localization.getString("precio_tension"),
                modifier = Modifier
                    .clickable {
                        // Mostrar el teclado para 'tension'
                        keyboardController?.show()
                    }
                    .padding(8.dp)
            )

            // Entrada de tensión
            TextField(
                value = tension,
                onValueChange = { if (it.all { char -> char.isDigit() }) tension = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar la tarifa
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
                        keyboardController?.hide() // Ocultar el teclado al guardar la tarifa
                    } else {
                        mostrarNotificacion("Por favor, completa todos los campos.")
                    }
                },
                text = Localization.getString("calcular")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para regresar
            Boton_Naranja(
                onClick = {
                    onAceptarClick()
                    keyboardController?.hide()
                },
                text = Localization.getString("regresar")
            )
        }
    }
}

// Función para mostrar una notificación en consola
fun mostrarNotificacion(mensaje: String) {
    println(mensaje)
}