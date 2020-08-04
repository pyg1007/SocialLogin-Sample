package com.sociallogin.kakaologin

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.kakao.auth.KakaoSDK

class App: Application() {

    companion object{
        var instance : App? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSDK.init(KakaoSdkAdapter())
        val appLifecycleObserver = AppLifecycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getAppContext(): App{
        if (instance == null){
            throw IllegalStateException("This Application does not inherit com.sociallogin.kakaologin")
        }
        return instance!!
    }
}