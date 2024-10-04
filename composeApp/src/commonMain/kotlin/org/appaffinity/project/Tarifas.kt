package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Clase de datos para almacenar las tarifas
@Serializable
data class Tarifa(val peso: String, val altura: String, val tension: String)

// Definición expect para guardar y cargar tarifas desde archivo
expect fun guardarTarifaEnArchivo(filePath: String, tarifa: Tarifa)
expect fun cargarTarifaDesdeArchivo(filePath: String): Tarifa?

@Composable
fun TarifaScreen(onClose: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    var tarifaGuardada by remember { mutableStateOf<Tarifa?>(null) }
    val filePath = "Tarifas.json" // Usa un nombre de archivo común

    // Cargar tarifas al iniciar
    LaunchedEffect(Unit) {
        tarifaGuardada = cargarTarifaDesdeArchivo(filePath)
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
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            tarifaGuardada?.let { tarifa ->
                Text(text = "Peso: ${tarifa.peso}", fontSize = 20.sp)
                Text(text = "Altura: ${tarifa.altura}", fontSize = 20.sp)
                Text(text = "Tensión: ${tarifa.tension}", fontSize = 20.sp)
            } ?: Text(text = "No hay tarifas disponibles", fontSize = 20.sp)

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

            Button(
                onClick = {
                    val tarifa = Tarifa(
                        peso = "${(peso.toInt())} céntimos",
                        altura = "${(altura.toInt())} céntimos",
                        tension = "${(tension.toInt())} céntimos"
                    )

                    guardarTarifaEnArchivo(filePath, tarifa)
                    peso = ""
                    altura = ""
                    tension = ""

                    tarifaGuardada = cargarTarifaDesdeArchivo(filePath)
                    mostrarNotificacion("Tarifa guardada exitosamente.")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = Localization.getString("calcular"))
            }

            Button(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = Localization.getString("regresar"))
            }
        }
    }
}

// Función para mostrar notificaciones
fun mostrarNotificacion(mensaje: String) {
    println(mensaje)
}
