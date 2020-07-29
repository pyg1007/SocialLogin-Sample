package com.sociallogin.googlelogin

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class App : Application() {

    companion object{
        fun getInstance(): FirebaseAuth{
            return FirebaseAuth.getInstance()
        }
    }

}