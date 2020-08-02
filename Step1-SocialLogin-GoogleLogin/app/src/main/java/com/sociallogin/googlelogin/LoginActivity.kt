package com.sociallogin.googlelogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sociallogin.googlelogin.databinding.ActivityLoginAcitivyBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginActivityBinding: ActivityLoginAcitivyBinding
    private val signInResult = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_acitivy)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleButton()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = App.getInstance().currentUser
        if (currentUser!= null)
            signIn()
    }

    private fun googleButton() {
        loginActivityBinding.googleSignInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, signInResult)
    }

    private fun successLogin(currentUser: FirebaseUser?) {
        Intent(this, MainActivity::class.java).also { intent ->
            currentUser?.let {
                intent.putExtra("Email", it.email.toString())
            }
            startActivity(intent)
            finish()
        }
    }

    private fun firebaseWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        App.getInstance().signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = App.getInstance().currentUser
                successLogin(user)
            } else {
                Toast.makeText(this, "Login Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == signInResult) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }
}