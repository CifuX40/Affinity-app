// PlataformaSelector.kt
package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlataformaSelector(onPlatformSelected: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(Res.drawable.fondo_de_pantalla),
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
            Text(text = "Selecciona tu plataforma:", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onPlatformSelected("Windows") }) {
                Text("Windows")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { onPlatformSelected("Android") }) {
                Text("Android")
            }
        }
    }
}
