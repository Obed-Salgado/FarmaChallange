package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class GetQuestionDataUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {

    suspend operator fun invoke(idNivel: String, idRonda: String): ResponseState<List<Pregunta>> =
        repoPregunta.getQuestionsData(idNivel, idRonda)
}