package com.sociallogin.kakaologin

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kakao.auth.Session

class AppLifecycleObserver(): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground(){
        Log.e("Observer", "Go ForeGround")
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground(){
        Log.e("Observer", "Go BackGround")
    }
}