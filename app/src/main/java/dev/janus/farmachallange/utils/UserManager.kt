package dev.janus.farmachallange.utils

import dev.janus.farmachallange.data.model.Usuario

object UserManager {

    private lateinit var user: Usuario

    fun setUserId(id: String) {
        this.user.id = id
    }

    fun setUser(user: Usuario) {
        this.user = user
    }

    fun decrementHearts() {
        if(this.user.corazones > 0)
            this.user.corazones -= 1
    }

    fun incrementHearts(){
        if(this.user.corazones < 12)
            this.user.corazones += 1
    }

    fun getHearts(): Int = user.corazones

    fun getInstanceUser(): Usuario {
        if (user != null)
            return user
        else {
            throw Exception("El id de usuario no ha sido asignado")
        }
    }
}