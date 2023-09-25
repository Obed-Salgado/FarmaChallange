package dev.janus.farmachallange.data.model

data class Pregunta(
    val id: Int = 0,
    val pregunta: String = "",
    val respuesta: String = "",
    val distractor: List<String> = emptyList(),
    val descripcion: String = ""
)