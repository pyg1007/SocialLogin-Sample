package com.sociallogin.kakaologin

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.sociallogin.kakaologin.databinding.ActivityLoginAcitivyBinding
import io.reactivex.disposables.CompositeDisposable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: ActivityLoginAcitivyBinding
    private val session = SessionCallback(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_acitivy)


        Session.getCurrentSession().addCallback(session)

        login()
    }

    private fun login(){
        loginActivityBinding.KakaoLoginButton.setOnClickListener {
            Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, this@LoginActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(session)
    }

    private fun getHashKey(): String?{
        val packageInfo: PackageInfo
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )

                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures){
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), NO_WRAP))
                }
            }else{
                packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ) ?: return null
                for (signature in packageInfo.signatures){
                    try{
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return String(Base64.encode(md.digest(), NO_WRAP))
                    }catch (e : NoSuchAlgorithmException){
                        e.printStackTrace()
                    }
                }
            }
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }catch (e: NoSuchAlgorithmException){
            e.printStackTrace()
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            Log.e("data", data.toString())
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}