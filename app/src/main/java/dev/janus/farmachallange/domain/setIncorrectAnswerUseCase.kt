package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class setIncorrectAnswerUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {
    operator fun invoke(nivel: Int, ronda: Int, numPregunta: Int, respuesta: String, pregunta: String) {
        repoPregunta.setIncorrectAnswer(nivel, ronda, numPregunta, respuesta, pregunta)
    }
}