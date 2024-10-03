package org.appaffinity.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuUsuario(
                onNavigateToTecnico = { /* Implementar acción para ir a Técnico */ },
                onNavigateToIdioma = { /* Implementar acción para ir a Idioma */ },
                onNavigateToFechaHora = { /* Implementar acción para ir a Fecha y Hora */ },
                onNavigateToTarifas = { /* Implementar acción para ir a Tarifas */ },
                onNavigateToFicha = { /* Implementar acción para ir a Ficha */ }
            )
        }
    }
}
