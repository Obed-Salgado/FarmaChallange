package dev.janus.farmachallange.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.janus.farmachallange.data.model.Usuario
import dev.janus.farmachallange.utils.UserManager
import javax.inject.Inject

class RepoUsuarios @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun registerUser(
        nombre: String,
        usuario: String,
        matricula: String,
        email: String,
        password: String,
        urlIcon: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        checkIfUserExists(usuario, email) { userExists ->
            if (userExists) {
                onFailure("El nombre de usuario o correo electrónico ya están en uso.")
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userId = user?.uid
                            if (userId != null) {
                                val userDoc = db.collection("usuarios").document(userId)
                                val userData = hashMapOf(
                                    "nombre" to nombre,
                                    "usuario" to usuario,
                                    "matricula" to matricula,
                                    "email" to email,
                                    "corazones" to 5,
                                    "monedas" to 0,
                                    "urlIcon" to urlIcon
                                )
                                userDoc.set(userData)
                                    .addOnSuccessListener {
                                        // Usuario creado exitosamente
                                        onSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        onFailure(e.message ?: "Error desconocido")
                                    }
                            } else {
                                onFailure("Error al obtener el ID de usuario.")
                            }
                        } else {
                            onFailure(task.exception?.message ?: "Error desconocido")
                        }
                    }
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        UserManager.setUser(Usuario())
                        UserManager.setUserId(userId)
                        onSuccess()
                    } else {
                        onFailure("Error al obtener el ID de usuario.")
                    }
                } else {
                    onFailure("¡Correo o Contraseña incorrecto!")
                }
            }
    }

    fun getUserData(idUser: String?): LiveData<Usuario?> {
        val mutableData = MutableLiveData<Usuario?>()

        if (idUser == null) {
            mutableData.value = null
        } else {
            db.collection("usuarios").document(idUser)
                .addSnapshotListener { documentSnapshot, exception ->
                    if (exception != null) {
                        // Manejar el error, si es necesario
                        mutableData.value = null
                        return@addSnapshotListener
                    }

                    if ((documentSnapshot != null) && documentSnapshot.exists()) {

                       val usuario = documentSnapshot.toObject(Usuario::class.java)

                        usuario?.id = idUser
                        if ((usuario?.usuario != null) && (usuario.corazones > 0)) {
                            UserManager.setUser(usuario)
                            mutableData.value = usuario

                        } else {
                            mutableData.value = null
                        }
                    } else {
                        mutableData.value = null
                    }
                }
        }
        return mutableData
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