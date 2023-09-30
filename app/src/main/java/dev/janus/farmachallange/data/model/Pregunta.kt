package dev.janus.farmachallange.data.model

data class Pregunta(
    val pregunta: String = "",
    val respuesta: String = "",
    val distractores: List<String> = emptyList(),
    val descripcion: String = ""
)