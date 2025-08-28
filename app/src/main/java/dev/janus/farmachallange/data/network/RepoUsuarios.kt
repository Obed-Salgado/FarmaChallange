package dev.janus.farmachallange.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.janus.farmachallange.data.model.ResponseState
import dev.janus.farmachallange.data.model.UserRegister
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.utils.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepoUsuarios @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    suspend fun registerUser(
        userInfo: UserRegister
    ): ResponseState<String> {
        return withContext(Dispatchers.IO){
            try {
                suspendCoroutine { continuation ->
                    checkIfUserExists(userInfo.userName, userInfo.email) { userExists ->
                        if (userExists) {
                            continuation.resume(ResponseState.Error("El nombre de usuario o correo electrónico ya están en uso."))
                        } else {
                            auth.createUserWithEmailAndPassword(userInfo.email, userInfo.password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        val userId = user?.uid
                                        if (userId != null) {
                                            val userDoc = db.collection("usuarios").document(userId)
                                            val userData = hashMapOf(
                                                "nombre" to userInfo.name,
                                                "usuario" to userInfo.userName,
                                                "matricula" to userInfo.tuition,
                                                "email" to userInfo.email,
                                                "corazones" to 5,
                                                "monedas" to 0,
                                                "urlIcon" to userInfo.urlIcon
                                            )
                                            userDoc.set(userData)
                                                .addOnSuccessListener {
                                                    // Usuario creado exitosamente
                                                    continuation.resume(ResponseState.Success("Usuario creado exitosamente"))
                                                }
                                                .addOnFailureListener { exception ->
                                                    continuation.resumeWithException(exception)
                                                }
                                        }
                                    }
                                }.addOnFailureListener { exception ->
                                    continuation.resumeWithException(exception)
                                }
                        }
                    }
                }
            } catch (e: Exception){
                val message = when(e.message){
                    "We have blocked all requests from this device due to unusual activity. Try again later." ->
                        "Usuario bloqueado temporalmente por exceso de intentos"
                    else ->
                        "Error inesperado"
                }
                ResponseState.Error(message)
            }
        }
    }


    suspend fun loginUser(
        email: String,
        password: String
    ): ResponseState<String> {
        return withContext(Dispatchers.IO){
            try {
                suspendCoroutine { continuation ->
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = auth.currentUser?.uid
                                if (userId != null) {
                                    UserManager.setUser(Usuario())
                                    UserManager.setUserId(userId)
                                    continuation.resume(ResponseState.Success(""))
                                }
                            }
                        }.addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
            } catch (e: Exception){
                val message = when(e.message){
                    "We have blocked all requests from this device due to unusual activity. Try again later." ->
                        "Usuario bloqueado temporalmente por exceso de intentos"
                    "An internal error has occurred. [ INVALID_LOGIN_CREDENTIALS ]" ->
                        "¡Correo o Contraseña incorrecto!"
                    else ->
                        "Error inesperado"
                }
                ResponseState.Error(message)
            }
        }
    }

    fun logOutUser() {
        auth.signOut()
    }

    fun checkExistSession(): Boolean{
        auth.currentUser?.let {
            UserManager.setUser(Usuario())
            UserManager.setUserId(it.uid)
        }
        return auth.currentUser != null
    }

     fun getUserData(idUser: String?): Flow<Usuario> = callbackFlow {
        val eventDocument = db.collection("usuarios").document(idUser!!)
        val suscripcion = eventDocument.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot!!.exists()){
                val user = documentSnapshot.toObject(Usuario::class.java)
                user!!.id = documentSnapshot.id
                UserManager.setUser(user!!)
                trySend(user!!)
            }
            else{
                channel.close(firebaseFirestoreException?.cause)
            }
        }
         awaitClose{suscripcion.remove()}
    }


    private fun checkIfUserExists(usuario: String, email: String, callback: (Boolean) -> Unit) {
        // Verificar si el nombre de usuario o correo electrónico ya existen
        db.collection("usuarios")
            .whereEqualTo("usuario", usuario)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    callback(true) // El nombre de usuario ya existe
                } else {
                    db.collection("usuarios")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnSuccessListener { emailQuerySnapshot ->
                            callback(!emailQuerySnapshot.isEmpty) // El correo electrónico ya está en uso
                        }
                        .addOnFailureListener {
                            callback(false) // Error desconocido
                        }
                }
            }
            .addOnFailureListener {
                callback(false) // Error desconocido
            }
    }
}