package dev.janus.farmachallange.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import dev.janus.farmachallange.data.model.Level


fun provideList(): List<Level> = listOf(
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    ),
    Level(
        id = "123",
        nombre = "34",
        descripcion = "",
        icono = ""
    )
)

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun ImageView.loadImage(source: String) {
        Glide.with(this.rootView.context)
            .load(source)
            .into(this)
}