package com.sociallogin.kakaologin

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.kakao.auth.AccessTokenCallback
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.auth.authorization.accesstoken.AccessToken
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException

class SessionCallback(private val activity: Activity): ISessionCallback {

    override fun onSessionOpenFailed(exception: KakaoException?) {
        Log.e("SessionOpenFail", exception?.localizedMessage.toString())
    }

    override fun onSessionOpened() {
        requestMe()
    }

    private fun requestMe(){
        UserManagement.getInstance().me(object: MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
                result?.let {
                    val userAccount = it.kakaoAccount

                    if (userAccount != null){

                        val userEmail = userAccount.email

                        if (!userEmail.isNullOrEmpty()){
                            Log.e("Email is Not null", userEmail)
                            Intent(activity, MainActivity::class.java).also { intent->
                                intent.putExtra("Email", userEmail)
                                activity.startActivity(intent)
                            }
                        }else if (userAccount.emailNeedsAgreement() == OptionalBoolean.TRUE){
                            val scope :List<String> = listOf("account_email")

                            Session.getCurrentSession().updateScopes(activity, scope, object: AccessTokenCallback(){
                                override fun onAccessTokenReceived(accessToken: AccessToken?) {
                                    Log.e("accessToken", accessToken?.accessToken.toString())
                                }

                                override fun onAccessTokenFailure(errorResult: ErrorResult?) {
                                    Log.e("TokenFailure", errorResult.toString())
                                }
                            })

                        }else{
                            Log.e("null", "null")
                        }

                    }
                }

            }

            override fun onFailure(errorResult: ErrorResult?) {
                Log.e("Failure", errorResult.toString())
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.e("SessionClosed", errorResult.toString())
            }
        })
    }
}