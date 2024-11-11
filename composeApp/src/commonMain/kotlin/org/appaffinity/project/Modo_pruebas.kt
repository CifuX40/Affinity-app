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
import kotlin.math.*

// Definición de la estructura del JSON
@Serializable
data class Medidas(
    val centimetros: Int,
    val gramos: Int,
    val tension: Int
)

@Composable
fun ModoPruebas(onBack: () -> Unit) {
    // Estado para los valores ingresados por el usuario
    var inputCentimetros by remember { mutableStateOf("") }
    var inputGramos by remember { mutableStateOf("") }
    var inputTension by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // Cargar datos del archivo JSON
    val medidasJson = loadMedidasFromJson("org/appaffinity/project/Modo_pruebas.json")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco),
        contentAlignment = Alignment.Center
    ) {
        // Fondo de pantalla
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenido de la pantalla "Modo pruebas"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // Título "Modo Pruebas"
            Text(
                text = "Modo Pruebas",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                color = Negro
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de entrada para los valores de medidas
            OutlinedTextField(
                value = inputCentimetros,
                onValueChange = { inputCentimetros = it },
                label = { Text("Ingrese Centímetros") }
            )
            OutlinedTextField(
                value = inputGramos,
                onValueChange = { inputGramos = it },
                label = { Text("Ingrese Gramos") }
            )
            OutlinedTextField(
                value = inputTension,
                onValueChange = { inputTension = it },
                label = { Text("Ingrese Tensión") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para verificar los valores
            Button(
                onClick = {
                    // Convertir valores ingresados y verificar con margen de error
                    val isCorrect = verifyValues(
                        medidasJson,
                        inputCentimetros.toIntOrNull() ?: 0,
                        inputGramos.toIntOrNull() ?: 0,
                        inputTension.toIntOrNull() ?: 0
                    )
                    resultado = if (isCorrect) "OK" else "Error"
                }
            ) {
                Text("Verificar")
            }

            // Mensaje de resultado
            Text(
                text = resultado,
                color = if (resultado == "OK") Verde else Rojo,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver atrás
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}

// Función para cargar los datos del JSON
fun loadMedidasFromJson(path: String): Medidas {
    val jsonContent = File(path).readText()
    return Json.decodeFromString(jsonContent)
}

// Función para verificar los valores ingresados con un margen de error
fun verifyValues(medidas: Medidas, centimetros: Int, gramos: Int, tension: Int, margen: Int = 5): Boolean {
    return abs(medidas.centimetros - centimetros) <= margen &&
            abs(medidas.gramos - gramos) <= margen &&
            abs(medidas.tension - tension) <= margen
}
