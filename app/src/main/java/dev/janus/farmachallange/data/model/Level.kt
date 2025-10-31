package dev.janus.farmachallange.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    var id:String ="",
    val nombre:String ="",
    val descripcion: String="",
    val icono: String =""
): Parcelable
