package dev.janus.farmachallange.data.model

data class UserRegister(
    val name: String,
    val userName: String,
    val tuition: String, //matricula
    val email: String,
    val password: String,
    val urlIcon: String
)
