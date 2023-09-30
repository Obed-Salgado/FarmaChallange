package dev.janus.farmachallange.domain

import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.network.RepoPregunta
import javax.inject.Inject

class getQuestionDataUseCase @Inject constructor(private val repoPregunta: RepoPregunta) {

    suspend operator fun invoke(idNivel:String, idRonda:String): Pregunta?{
        val pregunta = repoPregunta.getQuestionsData(idNivel, idRonda)
        if (!pregunta.isNullOrEmpty()){
            val randomNumber = (pregunta.indices).random()
            return pregunta[randomNumber]
        }
        return null
    }
}