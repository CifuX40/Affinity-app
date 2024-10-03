package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                Button(
                    onClick = {
                        showDialog = false
                        onClose()
                    },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF009EE0)
                    )
                ) {
                    Text(Localization.getString("aceptar"), color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = onClose,
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF009EE0)
                    )
                ) {
                    Text(Localization.getString("regresar"), color = Color.White)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(
                Res.drawable.fondo_de_pantalla
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                Localization.getString("selecciona_opcion"),
                fontSize = 24.sp,
                color = Color(0xFF1A171B)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    selectedOption = Localization.getString("fichas_farmaceuticas")
                    showDialog = true
                },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(
                        0xFF009EE0
                    )
                )
            ) {
                Text(Localization.getString("fichas_farmaceuticas"), color = Color.White)
            }

            Button(
                onClick = {
                    selectedOption = Localization.getString("euros")
                    showDialog = true
                },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(
                        0xFF009EE0
                    )
                )
            ) {
                Text(Localization.getString("euros"), color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onClose() },
                modifier = Modifier.padding(8.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(
                        0xFF009EE0
                    )
                )
            ) {
                Text(Localization.getString("regresar"), color = Color.White)
            }
        }
    }
}
