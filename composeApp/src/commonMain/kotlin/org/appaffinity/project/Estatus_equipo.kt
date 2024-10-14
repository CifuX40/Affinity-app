package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.json.*

@Composable
fun EstatusEquipoScreen(onValid: () -> Unit, onError: (String) -> Unit) {
    var isValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Simula la obtención del JSON de SBC (este puede venir de una red o base de datos)
        val json = """{
            "status": "ok",
            "data": {
                "device_id": "12345",
                "version": "1.0.0"
            }
        }"""

        // Simula la validación del JSON
        try {
            val parsedJson = Json.parseToJsonElement(json).jsonObject
            if (!validateJson(parsedJson)) {
                isValid = false
                errorMessage = "Datos inválidos"
            }
        } catch (e: Exception) {
            isValid = false
            errorMessage = "Error al procesar el JSON"
        }
    }

    if (isValid) {
        // Si el JSON es válido, llama la función onValid
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "JSON válido, continuando...")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onValid() }) {
                Text(text = "Ir a Menu Usuario")
            }
        }
    } else {
        // Si el JSON es inválido, muestra un mensaje de error
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Error: $errorMessage", color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onError(errorMessage) }) {
                Text(text = "Reintentar")
            }
        }
    }
}

fun validateJson(jsonObject: JsonObject): Boolean {
    // Aquí deberías agregar la lógica de validación basada en el contenido esperado del JSON
    return jsonObject["status"]?.jsonPrimitive?.content == "ok"
}
