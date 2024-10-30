package org.appaffinity.project

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object Localization {
    private val strings = mapOf(
        "Español" to mapOf(
            "menu_usuario" to "Menú de Usuario",
            "idioma" to "Idioma",
            "recaudacion" to "Recaudación",
            "tarifas" to "Tarifas",
            "fecha_hora" to "Fecha y Hora",
            "ficha" to "Ficha",
            "contrasena" to "Contraseña",
            "selecciona_idioma" to "Selecciona un idioma",
            "aceptar" to "Aceptar",
            "automático" to "Automático",
            "manual" to "Manual",
            "fecha_input_label" to "Formato: dd-MM-yyyy",
            "hora_input_label" to "Formato: HH:mm",
            "guardar" to "Guardar",
            "cancelar" to "Cancelar",
            "precio_peso" to "Precio para peso",
            "precio_altura" to "Precio para altura",
            "precio_tension" to "Precio para tensión",
            "calcular" to "Calcular",
            "regresar" to "Regresar",
            "selecciona_opcion" to "Selecciona una opción:",
            "fichas_farmaceuticas" to "Fichas Farmacéuticas",
            "euros" to "Euros",
            "seleccion_confirmada" to "Selección confirmada",
            "has_seleccionado" to "Has seleccionado: {option}",
            "enviar_video" to "Enviar vídeo",
            "videos_encontrados" to "Videos encontrados: {option}",
            "seleccionar_y_reproducir_video" to "Seleccionar y reproducir vídeo",
            "no_videos_encontrados" to "No se encontraron videos en los directorios especificados.",
            "no_archivo_seleccionado" to "No se ha seleccionado ningún archivo",
            "enviando_video" to "Enviando video: {option}...",
            "video_enviado_exito" to "Video enviado exitosamente!",
            "no_video_para_enviar" to "No se ha seleccionado ningún video para enviar.",
            "volver" to "Volver"
        ),
        "Inglés" to mapOf(
            "menu_usuario" to "User Menu",
            "idioma" to "Language",
            "recaudacion" to "Revenue",
            "tarifas" to "Rates",
            "fecha_hora" to "Date and Time",
            "ficha" to "Record",
            "contrasena" to "Password",
            "selecciona_idioma" to "Select a language",
            "aceptar" to "Accept",
            "automático" to "Automatic",
            "manual" to "Manual",
            "fecha_input_label" to "Format: dd-MM-yyyy",
            "hora_input_label" to "Format: HH:mm",
            "guardar" to "Save",
            "cancelar" to "Cancel",
            "precio_peso" to "Price for weight",
            "precio_altura" to "Price for height",
            "precio_tension" to "Price for tension",
            "calcular" to "Calculate",
            "regresar" to "Back",
            "selecciona_opcion" to "Select an option:",
            "fichas_farmaceuticas" to "Pharmaceutical Records",
            "euros" to "Euros",
            "seleccion_confirmada" to "Selection confirmed",
            "has_seleccionado" to "You have selected: {option}",
            "enviar_video" to "Send video",
            "videos_encontrados" to "Videos found: {option}",
            "seleccionar_y_reproducir_video" to "Select and play video",
            "no_videos_encontrados" to "No videos found in the specified directories.",
            "no_archivo_seleccionado" to "No file selected",
            "enviando_video" to "Sending video: {option}...",
            "video_enviado_exito" to "Video sent successfully!",
            "no_video_para_enviar" to "No video selected to send.",
            "volver" to "Back"
        ),
        "Francés" to mapOf(
            "menu_usuario" to "Menu de l'utilisateur",
            "idioma" to "Langue",
            "recaudacion" to "Recettes",
            "tarifas" to "Tarifs",
            "fecha_hora" to "Date et Heure",
            "ficha" to "Dossier",
            "contrasena" to "Mot de passe",
            "selecciona_idioma" to "Sélectionnez une langue",
            "aceptar" to "Accepter",
            "automatique" to "Automatique",
            "manual" to "Manuel",
            "fecha_input_label" to "Format: dd-MM-yyyy",
            "hora_input_label" to "Format: HH:mm",
            "guardar" to "Sauvegarder",
            "cancelar" to "Annuler",
            "precio_peso" to "Prix pour le poids",
            "precio_altura" to "Prix pour la hauteur",
            "precio_tension" to "Prix pour la tension",
            "calcular" to "Calculer",
            "regresar" to "Retour",
            "selecciona_opcion" to "Sélectionnez une option:",
            "fichas_farmaceuticas" to "Fiches Pharmaceutiques",
            "euros" to "Euros",
            "seleccion_confirmada" to "Sélection confirmée",
            "has_seleccionado" to "Vous avez sélectionné : {option}",
            "enviar_video" to "Envoyer une vidéo",
            "videos_encontrados" to "Vidéos trouvées : {option}",
            "seleccionar_y_reproducir_video" to "Sélectionner et lire la vidéo",
            "no_videos_encontrados" to "Aucune vidéo trouvée dans les répertoires spécifiés.",
            "no_archivo_seleccionado" to "Aucun fichier sélectionné",
            "enviando_video" to "Envoi de la vidéo : {option}...",
            "video_enviado_exito" to "Vidéo envoyée avec succès !",
            "no_video_para_enviar" to "Aucune vidéo sélectionnée à envoyer.",
            "volver" to "Retour"
        ),
        "Portugués" to mapOf(
            "menu_usuario" to "Menu de Usuário",
            "idioma" to "Idioma",
            "recaudacion" to "Arrecadação",
            "tarifas" to "Taxas",
            "fecha_hora" to "Data e Hora",
            "ficha" to "Registro",
            "contrasena" to "Senha",
            "selecciona_idioma" to "Selecione um idioma",
            "aceitar" to "Aceitar",
            "automático" to "Automático",
            "manual" to "Manual",
            "fecha_input_label" to "Formato: dd-MM-yyyy",
            "hora_input_label" to "Formato: HH:mm",
            "guardar" to "Salvar",
            "cancelar" to "Cancelar",
            "precio_peso" to "Preço para peso",
            "precio_altura" to "Preço para altura",
            "precio_tension" to "Preço para tensão",
            "calcular" to "Calcular",
            "regresar" to "Voltar",
            "selecciona_opcion" to "Selecione uma opção:",
            "fichas_farmaceuticas" to "Fichas Farmacêuticas",
            "euros" to "Euros",
            "seleccion_confirmada" to "Seleção confirmada",
            "has_seleccionado" to "Você selecionou: {option}",
            "enviar_video" to "Enviar vídeo",
            "videos_encontrados" to "Vídeos encontrados: {option}",
            "seleccionar_y_reproducir_video" to "Selecionar e reproduzir vídeo",
            "no_videos_encontrados" to "Nenhum vídeo encontrado nos diretórios especificados.",
            "no_archivo_seleccionado" to "Nenhum arquivo selecionado",
            "enviando_video" to "Enviando vídeo: {option}...",
            "video_enviado_exito" to "Vídeo enviado com sucesso!",
            "no_video_para_enviar" to "Nenhum vídeo selecionado para enviar.",
            "volver" to "Voltar"
        )
    )

    // Estado mutable para el idioma actual
    var currentLanguage by mutableStateOf("Español")
        private set

    // Obtener la traducción de una clave
    fun getString(key: String, option: String? = null): String {
        val translation = strings[currentLanguage]?.get(key) ?: key
        return if (option != null) translation.replace("{option}", option) else translation
    }

    // Establecer el idioma y notificar cambios
    fun setLanguage(language: String) {
        currentLanguage = language
    }
}