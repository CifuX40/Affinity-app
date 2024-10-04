package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import androidx.compose.foundation.Image
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
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

data class Tarifa(val peso: String, val altura: String, val tension: String)

@Composable
fun TarifaScreen(onAceptarClick: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    var tarifaGuardada by remember { mutableStateOf<Tarifa?>(null) }
    val filePath = "Tarifas.json"

    Box(modifier = Modifier.fillMaxSize()) {
        // Asegúrate de que la ruta del recurso sea válida
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
                    if (peso.isNotEmpty() && altura.isNotEmpty() && tension.isNotEmpty()) {
                        val tarifa = Tarifa(
                            peso = "${(peso.toInt())} céntimos",
                            altura = "${(altura.toInt())} céntimos",
                            tension = "${(tension.toInt())} céntimos"
                        )
                        tarifaGuardada = tarifa // Guarda la tarifa
                        mostrarNotificacion("Tarifa guardada exitosamente.")
                    } else {
                        mostrarNotificacion("Por favor, completa todos los campos.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = Localization.getString("calcular"))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAceptarClick,
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
