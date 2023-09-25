package dev.janus.farmachallange.data.model

data class Usuario (
    var id: String = "",
    val usuario: String? = "",
    val corazones: Int = 0,
    val monedas: Int = 0,
    val email: String = "",
    val nombre: String = "",
    val matricula: String = "",
    val urlIcon: String = ""
)