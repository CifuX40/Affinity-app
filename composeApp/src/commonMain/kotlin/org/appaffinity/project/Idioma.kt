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

// Pantalla que permite a los usuarios seleccionar un idioma y guardar su selección.
@Composable
fun IdiomaScreen(onAceptarClick: () -> Unit) {
    // Estado para almacenar el idioma seleccionado, se inicializa con el idioma actual de la aplicación.
    var idiomaSeleccionado by remember { mutableStateOf(Localization.currentLanguage) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                Localization.getString("selecciona_idioma"),
                fontSize = 30.sp,
                color = Naranja,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de idiomas disponibles. Para cada idioma se crea un componente `Row`.
            listOf("Español", "Inglés", "Francés", "Portugués").forEach { idioma ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            idiomaSeleccionado = idioma
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = idioma,
                        fontSize = 18.sp,
                        color = Color.Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Boton_Naranja(
                onClick = {
                    Localization.setLanguage(idiomaSeleccionado)
                    onAceptarClick()
                },
                text = Localization.getString("aceptar")
            )
        }
    }
}