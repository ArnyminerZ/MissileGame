package auth

import kotlinx.coroutines.flow.Flow

expect object AuthState {
    fun isLoggedIn(): Flow<Boolean>
}
