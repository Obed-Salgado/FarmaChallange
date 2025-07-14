package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class GetQuestionDataUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {

    suspend operator fun invoke(idNivel: String, idRonda: String): List<Pregunta> =
        repoPregunta.getQuestionsData(idNivel, idRonda)
}