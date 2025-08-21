package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class GetRondaUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {
    suspend operator fun invoke(idNivel:String): ResponseState<List<Ronda>> = repoPregunta.getRonda(idNivel)
}