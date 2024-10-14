package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.json.*
import kotlinx.coroutines.*

@Composable
fun EstatusEquipoScreen(onValid: () -> Unit, onError: (String) -> Unit) {
    var isValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var loadingText by remember { mutableStateOf("Cargando sistema, espere...") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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

    // Inicia el temporizador de 5 segundos para cambiar el estado del botón y el texto
    LaunchedEffect(Unit) {
        delay(5000)
        loadingText = "Sistema cargado"
        isButtonEnabled = true
    }

    if (isValid) {
        // Si el JSON es válido, muestra el texto y el botón
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = loadingText)
            Spacer(modifier = Modifier.height(16.dp))
            // Reemplazamos el Button por Boton_Naranja y controlamos si está habilitado o no
            Boton_Naranja(texto = "Ir a Menu Usuario", onClick = { onValid() }, enabled = isButtonEnabled)
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
            // Reemplazamos el Button por Boton_Naranja
            Boton_Naranja(texto = "Reintentar", onClick = { onError(errorMessage) }, enabled = true)
        }
    }
}

fun validateJson(jsonObject: JsonObject): Boolean {
    // Aquí deberías agregar la lógica de validación basada en el contenido esperado del JSON
    return jsonObject["status"]?.jsonPrimitive?.content == "ok"
}

// Función Boton_Naranja que se asegura de que todos los botones se vean iguales
@Composable
fun Boton_Naranja(texto: String, onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled, // Habilita o deshabilita el botón según el valor de enabled
        colors = ButtonDefaults.buttonColors(backgroundColor = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = texto, color = if (enabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)
    }
}
