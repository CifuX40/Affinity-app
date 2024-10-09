package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

// Pantalla que permite a los usuarios seleccionar un idioma y guardar su selección.
@Composable
fun IdiomaScreen(onAceptarClick: () -> Unit) {
    // Estado para almacenar el idioma seleccionado, se inicializa con el idioma actual de la aplicación.
    var idiomaSeleccionado by remember { mutableStateOf(Localization.currentLanguage) }

    // Contenedor que cubre toda la pantalla.
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo que cubre toda la pantalla.
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla), // Carga la imagen de fondo.
            contentDescription = null, // No necesita descripción accesible.
            modifier = Modifier.fillMaxSize(), // Hace que la imagen cubra todo el tamaño disponible.
            contentScale = ContentScale.Crop // Ajusta la imagen para que llene el área sin perder proporción.
        )

        // Columna que organiza los elementos centrados tanto vertical como horizontalmente.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize() // La columna ocupa todo el tamaño de la pantalla.
                .padding(16.dp) // Añade un padding de 16dp alrededor del contenido.
        ) {
            // Título que indica al usuario que seleccione un idioma.
            Text(
                Localization.getString("selecciona_idioma"), // Obtiene el texto "Selecciona idioma" desde Localization.
                fontSize = 30.sp, // Tamaño del texto.
                color = Naranja, // Color del texto (color personalizado).
                fontWeight = FontWeight.Bold // Texto en negrita.
            )

            Spacer(modifier = Modifier.height(16.dp)) // Añade un espacio vertical de 16dp entre el título y las opciones.

            // Lista de idiomas disponibles. Para cada idioma se crea un componente `Row`.
            listOf("Español", "Inglés", "Francés", "Portugués").forEach { idioma ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // El `Row` ocupa todo el ancho disponible.
                        .clickable { idiomaSeleccionado = idioma } // Cambia el idioma seleccionado al hacer clic.
                        .padding(vertical = 8.dp), // Padding vertical de 8dp alrededor de cada idioma.
                    verticalAlignment = Alignment.CenterVertically, // Alinea el texto verticalmente en el centro.
                    horizontalArrangement = Arrangement.Center // Centra el contenido horizontalmente.
                ) {
                    // Texto que muestra el nombre del idioma.
                    Text(text = idioma, fontSize = 18.sp, color = Color.Blue) // Texto en azul y tamaño de fuente 18sp.
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // Añade un espacio vertical de 32dp entre las opciones y el botón de aceptar.

            // Reemplaza el botón original por Boton_Naranja
            Boton_Naranja(
                onClick = {
                    Localization.setLanguage(idiomaSeleccionado) // Establece el idioma seleccionado en la configuración.
                    onAceptarClick() // Llama a la función `onAceptarClick` que vuelve a la pantalla anterior.
                },
                text = Localization.getString("aceptar") // Texto del botón "Aceptar" desde Localization.
            )
        }
    }
}
