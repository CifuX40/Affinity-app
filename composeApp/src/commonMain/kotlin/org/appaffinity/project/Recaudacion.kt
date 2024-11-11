package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.*
import kotlin.random.*

@Composable
fun Recaudacion(onBack: () -> Unit) {
    // Estados para los contadores con valores aleatorios entre 0 y 15
    var contadorPeso by remember { mutableStateOf(Random.nextInt(16)) }
    var contadorAltura by remember { mutableStateOf(Random.nextInt(16)) }
    var contadorTension by remember { mutableStateOf(Random.nextInt(16)) }

    // Función para generar valores aleatorios para los tres contadores
    fun cargarDatosAleatorios() {
        contadorPeso = Random.nextInt(16)
        contadorAltura = Random.nextInt(16)
        contadorTension = Random.nextInt(16)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Recaudación de servicios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Mostrar los contadores
            Text(text = "Servicio de Peso: $contadorPeso veces", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Servicio de Altura: $contadorAltura veces", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Servicio de Tensión: $contadorTension veces", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para regresar al MenuUsuario
            Boton_Naranja(
                onClick = onBack,
                text = "Volver al Menú de Usuario"
            )
        }
    }
}