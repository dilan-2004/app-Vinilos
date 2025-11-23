package com.example.vinilos.data.repository

import android.content.Context
import com.example.vinilos.data.local.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(context: Context) {

    private val auth = FirebaseAuth.getInstance()

    private val sessionManager = SessionManager(context)


    suspend fun login(email: String, password: String): Result<String> {
        return try {

            val result = auth.signInWithEmailAndPassword(email, password).await()

            val uid = result.user?.uid
                ?: return Result.failure(Exception("No se pudo obtener el UID del usuario"))

            sessionManager.saveUid(uid)
            Result.success(uid)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid =result.user?.uid ?:return Result.failure(Exception("No se pudo obtener el UID del usuario"))

            sessionManager.saveUid(uid)
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)


        }
    }

    suspend fun logout() {
        auth.signOut()
        sessionManager.clearSession()

    }
}