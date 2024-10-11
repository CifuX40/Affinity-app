package org.appaffinity.project

import androidx.compose.material.*
import androidx.compose.runtime.*

@Composable
fun Reiniciar(onReiniciar: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Reiniciar") },
        text = { Text("¿Estás seguro que quieres reiniciar el dispositivo?") },
        confirmButton = {
            Button(onClick = {
                onReiniciar() // Lógica para reiniciar el dispositivo
            }) {
                Text("Sí")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("No")
            }
        }
    )
}
