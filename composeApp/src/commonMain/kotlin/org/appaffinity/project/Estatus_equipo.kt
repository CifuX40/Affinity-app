package org.appaffinity.project

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.Serializable

@Serializable
data class EquipoStatus(val status: String, val message: String, val data: Data)

@Serializable
data class Data(val nombre_equipo: String, val estado: String)

class EquipoRepository {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getEquipoStatus(): EquipoStatus {
        return client.get("http://192.168.1.100:5000/status")  // Cambia la IP por la de tu SBC
    }
}
