package org.appaffinity.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Aquí, asegúrate de pasar el savedInstanceState al super
        super.onCreate(savedInstanceState)

        setContent {
            EstadoMaquina(onButtonClick = {
                // Acción a realizar al hacer clic en el botón
                println("Continuar botón clickeado")
            })
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    EstadoMaquina(onButtonClick = {
        // Acciones que desees ejecutar en el preview
        println("Continuar botón clickeado en el preview")
    })
}
