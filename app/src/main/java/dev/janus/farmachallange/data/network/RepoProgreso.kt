package dev.janus.farmachallange.data.network

import com.google.firebase.firestore.FirebaseFirestore
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RepoProgreso @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getLevelProgress(levels: List<Nivel>): ResponseState<List<Progress>> = withContext(Dispatchers.IO){
        val progressList = mutableListOf<Progress>()
        try {
            val id = UserManager.getInstanceUser().id
            for (level in levels){
                val documentSnapshot = suspendCancellableCoroutine { continuation ->
                    val requestDocument = db.collection("usuarios").document(id)
                        .collection("niveles").document(level.id)
                        .collection("rondas").whereEqualTo("completada", true)
                    requestDocument.get().addOnSuccessListener { document ->
                        continuation.resume(document)
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                }

                progressList.add(
                    Progress(
                        title = level.nombre,
                        description = level.descripcion,
                        icon = level.icono,
                        progress = (documentSnapshot.documents.size * 0.1).toFloat()
                    )
                )
            }

            if (progressList.isNotEmpty()) {
                ResponseState.Success(progressList)
            } else {
                ResponseState.Error("Empty list")
            }
        } catch (e: Exception){
            ResponseState.Error(e.message ?: "Error")
        }
    }
}