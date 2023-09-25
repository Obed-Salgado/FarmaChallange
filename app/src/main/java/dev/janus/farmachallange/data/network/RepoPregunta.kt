package dev.janus.farmachallange.data.network

import com.google.firebase.firestore.FirebaseFirestore
import dev.janus.farmachallange.data.model.Pregunta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepoPregunta @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getQuestionsData(): List<Pregunta> {
        return withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine { continuation ->
                val listaPreguntas = mutableListOf<Pregunta>()
                db.collection("preguntas").document("nivel1").collection("ronda1").get()
                    .addOnSuccessListener { result ->

                        for (document in result) {
                            val pregunta = document.toObject(Pregunta::class.java)
                            listaPreguntas.add(pregunta)
                        }
                        continuation.resume(listaPreguntas)
                    }
            }
        }
    }
}