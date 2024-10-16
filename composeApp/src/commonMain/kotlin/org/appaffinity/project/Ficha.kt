package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.*

// Pantalla que permite al usuario seleccionar entre varias opciones y muestra un diálogo de confirmación cuando se selecciona una opción.
@Composable
fun FichaScreen(onClose: () -> Unit) {
    var selectedOption by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = Localization.getString("seleccion_confirmada"),
                    color = Color(0xFF009EE0)
                )
            },
            text = {
                Text(
                    text = Localization.getString("has_seleccionado", selectedOption),
                    color = Color(0xFF009EE0)
                )
            },
            confirmButton = {
                Boton_Naranja(
                    onClick = {
                        showDialog = false
                        onClose()
                    },
                    text = Localization.getString("aceptar")
                )
            },
            dismissButton = {
                Boton_Naranja(
                    onClick = onClose,
                    text = Localization.getString("regresar")
                )
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize() // Box completamente transparente, no se agrega fondo
    ) {
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
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp)), // Fondo transparente
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                Localization.getString("selecciona_opcion"),
                fontSize = 24.sp,
                color = Color(0xFF1A171B)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Usa Boton_Naranja para todos los botones
            Boton_Naranja(
                onClick = {
                    selectedOption = Localization.getString("fichas_farmaceuticas")
                    showDialog = true
                },
                text = Localization.getString("fichas_farmaceuticas")
            )

            Boton_Naranja(
                onClick = {
                    selectedOption = Localization.getString("euros")
                    showDialog = true
                },
                text = Localization.getString("euros")
            )

            Spacer(modifier = Modifier.height(32.dp))

            Boton_Naranja(
                onClick = { onClose() },
                text = Localization.getString("regresar")
            )
        }
    }
}