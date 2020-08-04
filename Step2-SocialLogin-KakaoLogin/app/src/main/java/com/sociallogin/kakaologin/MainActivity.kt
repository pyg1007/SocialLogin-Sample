package com.sociallogin.kakaologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import com.kakao.usermgmt.request.LogoutRequest
import com.sociallogin.kakaologin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getEmail()
        logout()
    }

    private fun getEmail(){
        mainActivityBinding.ID.text = intent.getStringExtra("Email")
    }

    private fun logout(){
        mainActivityBinding.LogOut.setOnClickListener {
            kakaoUnLink()
        }
    }

    private fun kakaoLogOut(){
        UserManagement.getInstance().requestLogout(object: LogoutResponseCallback(){
            override fun onCompleteLogout() {
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
        })
    }

    private fun kakaoUnLink(){
        UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback(){
            override fun onSuccess(result: Long?) {
                kakaoLogOut()
            }

            override fun onFailure(errorResult: ErrorResult?) {
                Log.e("UnLinkFail", errorResult.toString())
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.e("UnLinkFail", "session closed " + errorResult.toString())
            }
        })
    }
}