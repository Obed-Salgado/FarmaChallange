package dev.janus.farmachallange.data.network


import com.google.firebase.firestore.FirebaseFirestore
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.data.model.Pregunta
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.Ronda
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoPregunta @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getQuestionsData(idNivel: String, idRonda: String): ResponseState<List<Pregunta>> {
        return withContext(Dispatchers.IO) {
            val questionList = mutableListOf<Pregunta>()
            val query =
                db.collection("preguntas").document(idNivel).collection("rondas").document(idRonda)
                    .collection("pregunta").get().await()
            for (document in query.documents) {
                val pregunta = document.toObject(Pregunta::class.java)
                pregunta?.let { questionList.add(it) }
            }
            if(questionList.isNotEmpty())
                ResponseState.Success(questionList)
            else
                ResponseState.Error("Empty list")
        }
    }

    suspend fun getLevelName(): ResponseState<List<Nivel>> = withContext(Dispatchers.IO) {
        try {
            val levelList = mutableListOf<Nivel>()
            val querySnapshot =
                db.collection("preguntas").get().await() // Usar await() para esperar la respuesta
            for (document in querySnapshot.documents) {
                val nivel = document.toObject(Nivel::class.java)
                nivel?.id = document.id
                nivel?.let { levelList.add(it) }
            }

            if(levelList.isNotEmpty())
                ResponseState.Success(levelList)
            else
                ResponseState.Error("Empty list")
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "Error")
        }
    }

    suspend fun getRonda(idNivel: String): ResponseState<List<Ronda>> =
        withContext(Dispatchers.IO) {
            try {
                val rondaList = mutableListOf<Ronda>()
                val query =
                    db.collection("preguntas").document(idNivel).collection("rondas").get().await()
                for (document in query.documents) {
                    val ronda = document.toObject(Ronda::class.java)
                    ronda?.id = document.id
                    ronda?.let { rondaList.add(ronda) }
                }
                if(rondaList.isNotEmpty())
                    ResponseState.Success(rondaList)
                else ResponseState.Error("Empty list")
            } catch(e:Exception) {
                ResponseState.Error(e.message ?: "Error")
            }
    }

    fun setIncorrectAnswer(nivel: Int, ronda: Int, numPregunta: Int, respuesta: String, pregunta: String){
        val question = hashMapOf(
            "nivel" to nivel,
            "ronda" to ronda,
            "numPregunta" to numPregunta,
            "respuesta" to respuesta,
            "pregunta" to pregunta
        )
        db.collection("usuarios").document(UserManager.getInstanceUser().id)
            .collection("preguntasIncorrectas")
            .add(question).addOnSuccessListener {

            }
            .addOnFailureListener { e ->

            }
    }
}