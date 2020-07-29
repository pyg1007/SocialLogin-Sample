package com.sociallogin.googlelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sociallogin.googlelogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getID()
    }

    private fun getID(){
        val email = intent.getStringExtra("Email")
        showID(email)
    }

    private fun showID(email: String?){
        if (email.isNullOrEmpty()){
            Toast.makeText(this, "Fail getLoginData", Toast.LENGTH_SHORT).show()
        }else{
            mainActivityBinding.ID.text = email
        }
    }
}