package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class getLevelUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {
    suspend operator fun invoke(): List<Nivel> = repoPregunta.getLevelName()

}