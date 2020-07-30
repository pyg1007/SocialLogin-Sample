package com.sociallogin.googlelogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.sociallogin.googlelogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        getID()
        mainActivityBinding.LogOut.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        removeAccountInfo()
    }

    private fun removeAccountInfo() {
        App.getInstance().currentUser?.let {
            it.delete().addOnCompleteListener {
                App.getInstance().signOut()
                Intent(this, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {
                Log.e("Failure", "Fail")
            }
        }
    }

    private fun getID() {
        val email = intent.getStringExtra("Email")
        showID(email)
    }

    private fun showID(email: String?) {
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Fail getLoginData", Toast.LENGTH_SHORT).show()
        } else {
            mainActivityBinding.ID.text = email
        }
    }

    override fun onBackPressed() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
        super.onBackPressed()
    }
}