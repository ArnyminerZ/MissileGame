package auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

actual object AuthState {
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    actual fun isLoggedIn(): Flow<Boolean> = callbackFlow {
        val listener = AuthStateListener { trySend(it.currentUser != null) }
        auth.addAuthStateListener(listener)

        trySend(auth.currentUser != null)

        awaitClose { auth.removeAuthStateListener(listener) }
    }
}
