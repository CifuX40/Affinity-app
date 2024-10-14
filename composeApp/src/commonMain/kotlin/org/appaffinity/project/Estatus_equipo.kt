package org.appaffinity.project

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.appaffinity.project.ui.theme.ComposeAppTheme
import androidx.compose.ui.unit.*

@Composable
fun EstatusEquipoScreen(navController: NavController) {
    var isValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
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

    if (isValid) {
        // Si el JSON es válido, muestra la opción de ir a Menu_usuario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "JSON válido, continuando...")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("menu_usuario") }) {
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
            Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Puede reintentar o cerrar la aplicación */ }) {
                Text(text = "Reintentar")
            }
        }
    }
}

fun validateJson(jsonObject: JsonObject): Boolean {
    // Aquí deberías agregar la lógica de validación basada en el contenido esperado del JSON
    return jsonObject["status"]?.jsonPrimitive?.content == "ok"
}

@Preview(showBackground = true)
@Composable
fun PreviewEstatusEquipoScreen() {
    ComposeAppTheme {
        EstatusEquipoScreen(navController = rememberNavController())
    }
}