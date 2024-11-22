package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.*

@Composable
fun CambiarContrasenaScreen(onBack: () -> Unit) {
    var contrasenaActual by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmacionContrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo que ocupa toda la pantalla
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Cambiar Contraseña",
                style = MaterialTheme.typography.h4,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            // Contraseña actual
            TextField(
                value = contrasenaActual,
                onValueChange = { contrasenaActual = it },
                label = { Text("Contraseña Actual") },
                visualTransformation = PasswordVisualTransformation(),
                isError = mensajeError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Nueva contraseña
            TextField(
                value = nuevaContrasena,
                onValueChange = { nuevaContrasena = it },
                label = { Text("Nueva Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = mensajeError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Confirmación de nueva contraseña
            TextField(
                value = confirmacionContrasena,
                onValueChange = { confirmacionContrasena = it },
                label = { Text("Confirmar Nueva Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = mensajeError.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Mostrar mensaje de error si hay
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            // Botón para cambiar la contraseña
            Button(
                onClick = {
                    // Verificar que todos los campos estén llenos
                    if (contrasenaActual.isEmpty() || nuevaContrasena.isEmpty() || confirmacionContrasena.isEmpty()) {
                        mensajeError = "Por favor, rellene todos los campos."
                    } else if (nuevaContrasena == confirmacionContrasena) {
                        if (contrasenaActual == "9876") {
                            mensajeError = ""
                            // Actualiza la contraseña
                            // Este es un lugar donde podrías actualizar la contraseña en tu sistema
                            onBack()
                        } else {
                            mensajeError = "La contraseña actual es incorrecta"
                        }
                    } else {
                        mensajeError = "Las nuevas contraseñas no coinciden"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar Contraseña")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de volver
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}