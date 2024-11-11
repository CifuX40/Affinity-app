package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.math.*

@Composable
fun Modo_pruebas() {
    val jsonString = loadJSON() // Función para cargar el JSON de las medidas
    val medidas = parseJSON(jsonString) // Función para parsear el JSON

    // Variables para almacenar las entradas del usuario
    var centimetros by remember { mutableStateOf("") }
    var gramos by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    // Función para comparar los valores con margen de error
    fun compararValores() {
        val margenError = 0.1 // 0.1 como margen de error
        val cmIngresados = centimetros.toDoubleOrNull()
        val gramosIngresados = gramos.toDoubleOrNull()
        val tensionIngresada = tension.toDoubleOrNull()

        if (cmIngresados != null && gramosIngresados != null && tensionIngresada != null) {
            val cmCorrecto = medidas.centimetros
            val gramosCorrectos = medidas.gramos
            val tensionCorrecta = medidas.tension

            // Comparar con margen de error
            val errorCm = abs(cmIngresados - cmCorrecto) <= margenError
            val errorGramos = abs(gramosIngresados - gramosCorrectos) <= margenError
            val errorTension = abs(tensionIngresada - tensionCorrecta) <= margenError

            mensaje = if (errorCm && errorGramos && errorTension) {
                "OK"
            } else {
                "Error"
            }
        } else {
            mensaje = "Por favor, introduce valores válidos."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Introduce las medidas para la comparación")
        Spacer(modifier = Modifier.height(20.dp))

        // Entrada para centímetros
        TextField(
            value = centimetros,
            onValueChange = { centimetros = it },
            label = { Text("Centímetros") },
            modifier = Modifier.fillMaxWidth()
        )

        // Entrada para gramos
        TextField(
            value = gramos,
            onValueChange = { gramos = it },
            label = { Text("Gramos") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Entrada para tensión
        TextField(
            value = tension,
            onValueChange = { tension = it },
            label = { Text("Tensión") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para comparar los valores
        Button(onClick = { compararValores() }) {
            Text("Comparar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar el resultado
        Text(text = mensaje, style = MaterialTheme.typography.body1)
    }
}

// Función para cargar el JSON de un archivo o de una cadena estática
fun loadJSON(): String {
    return """
        {
          "medidas": {
            "centimetros": 180,
            "gramos": 60,
            "tension": 220
          }
        }
    """
}

// Usamos Kotlinx Serialization para parsear el JSON
@Serializable
data class Medidas(val centimetros: Double, val gramos: Double, val tension: Double)

fun parseJSON(jsonString: String): Medidas {
    return Json.decodeFromString(jsonString)  // Decodificar el JSON utilizando Kotlinx Serialization
}
