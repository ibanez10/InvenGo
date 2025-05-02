package com.example.invengo

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Login dengan email dan password
    fun loginWithEmail(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Login failed") }
    }

    // Mendapatkan GoogleSignInClient
    fun getGoogleClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("73300987529-9bjrhq5jhm3da7rhuj9ppr61ic2o6tp1.apps.googleusercontent.com") // Ganti dengan web client ID dari Firebase console
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    // Login dengan Google
    fun loginWithGoogleIntent(data: Intent?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java) // Menggunakan ApiException untuk menangani kesalahan
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it.message ?: "Google Sign-In failed") }
        } catch (e: ApiException) {
            Log.w("AuthViewModel", "Google sign in failed", e)
            onError("Google Sign-In failed: ${e.statusCode}") // Menyediakan kode status untuk debugging
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error during Google sign in", e)
            onError(e.message ?: "Google Sign-In failed")
        }
    }
}