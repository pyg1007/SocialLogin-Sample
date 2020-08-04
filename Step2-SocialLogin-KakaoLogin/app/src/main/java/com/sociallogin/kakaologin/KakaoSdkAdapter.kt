package com.sociallogin.kakaologin

import com.kakao.auth.*

class KakaoSdkAdapter: KakaoAdapter(){

    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig { App().getAppContext() }
    }

    override fun getSessionConfig(): ISessionConfig {
        return object: ISessionConfig{
            override fun isSaveFormData(): Boolean {
                return true
            }

            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_ACCOUNT)
            }

            override fun isSecureMode(): Boolean {
                return true
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }
        }
    }
}