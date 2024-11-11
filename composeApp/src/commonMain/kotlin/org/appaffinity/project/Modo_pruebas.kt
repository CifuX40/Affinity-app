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
import org.jetbrains.compose.resources.*

@Composable
fun ModoPruebas(onBack: () -> Unit) {
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
                color = Negro // Asegúrate de que 'Negro' sea una variable de color válida
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción de la pantalla
            Text(
                text = "Esta es la pantalla de Modo Pruebas",
                style = MaterialTheme.typography.body1,
                color = Negro
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver atrás
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}