package dev.janus.farmachallange.data.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RepoEstadistica @Inject constructor(private val db: FirebaseFirestore) {

    fun uptdateHearts(userId:String?, hearts:Int){
        val userDc = db.collection("usuarios").document(userId!!)
        userDc.update("corazones", hearts).addOnFailureListener{
            throw Exception("Error al actualizazr campo")
        }
    }

    fun uptdateCoins(userId:String?, coins:Int){

        val userDc = db.collection("usuarios").document(userId!!)
        userDc.update("monedas", coins).addOnFailureListener{
            throw Exception("Error al actualizazr campo")
        }
    }
}