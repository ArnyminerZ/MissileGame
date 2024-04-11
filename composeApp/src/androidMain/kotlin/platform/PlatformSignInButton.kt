package platform

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.arnyminerz.missilegame.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
actual fun PlatformSignInButton() {
    val context = LocalContext.current

    val authResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("PlatformSignInButton", "Google sign in successful")
                } else {
                    Log.e("PlatformSignInButton", "Google sign in failed", task.exception)
                }
            }
        } catch (e: ApiException) {
            Log.e("PlatformSignInButton", "Google sign in failed", e)
        }
    }

    OutlinedButton(
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.WEB_CLIENT_ID)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            authResultLauncher.launch(googleSignInClient.signInIntent)
        }
    ) {
        Text("Sign in with Google")
    }
}
