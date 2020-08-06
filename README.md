# SocialLogin-Sample

this project is SocialLogin Sample

1. google Login
2. Kakao Login
3. Naver Login

with Kotlin based + singleton pattern + databinding  

### How to use databinding in Kotlin   
build.gradle(Module: app)   
<pre>
<code>
android{
  ...
  ...
    
  buildFeatures {
          dataBinding = true
  }
}
</code>
</pre>
    

## step1 Google Login

Google Login need firebase connect this url  
```
https://console.firebase.google.com/  
```
create firebase project and add android  
next, Authentication -> Sign-in-method -> Google use
    
first write Android App PackageName, NickName, SHA-1  
    
### How to get SHA-1
    Android Studio -> Gradle(Android studio right) -> My Application -> Task -> Android -> signingReport  
    
next, download google-services.json, your android project app folder input  

build.gradle(project: AppName)
```
buildscript{
  ...
  ...
  ...
  dependencies{
    ...
    //add
    classpath 'com.google.gms:google-services:4.3.3'
  }
  ...
}
```

builde.gradle(Module: app)
```
apply plugin: 'com.android.application'
//add
apply plugin: 'com.google.gms.google-services'
...
...
dependencies{
  ...
  //add
  //alt+Enter newest version
  //2020-07-29 newest version = 17.4.4, 19.3.2, 18.1.0
  implementation 'com.google.firebase:firebase-analytics:17.4.4'
  implementation 'com.google.firebase:firebase-auth:19.3.2'
  implementation 'com.google.android.gms:play-services-auth:18.1.0'
}
```

after, Sync Now

## step2 Kakao Login

Kakao Developers connect this url  
```
https://developers.kakao.com/  
```

Kakao Developer site login is required. After logging in, enter the Kakao Login product introduction and apply to use the Kakao API at the bottom.

After adding the application, enter the platform on the left and register the Android platform.

The package name and key hash are required. The package name is obtained from the Manifest, and the key hash is obtained as follows.

```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.e("HashKey : ", getHashKey().toString())
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
```

You can get the hash value by running the project and viewing the log. Add log values to the previous hash.

Register the native app key as Meta-data in the manifest.
```
<application
     ...
     ...>
    <meta-data
     android:name="com.kakao.sdk.AppKey"
     android:value="your_kakao_native_appKey" />
     ...
</application>
```
Set the necessary items in the consent item in the product setting column on the left.

## step3 Naver Login

Please access the url below  
```
https://developers.naver.com/docs/login/android/
```

Apply for the use of Open API on the site.  

Required permissions are checked as required.  

Next, go into the library view on that site.  

Download the latest version from Git as a zip.  

After unzipping the zip, move the .aar file in the naveridlogin android sdk folder into the android studio app-libs.  

### Where is libs
1. Change the Android in the upper left corner of Android Studio to Project.
2. Tap the folder to enter the app.
3. There is a libs folder.

Add the following to your gradle.  
build.gradle(Module: app)
```
dependencies {
  implementation files('libs/naveridlogin_android_sdk_4.2.6.aar')
}
```

Add the following to the ProGuard rule.  
proguard-rules.pro
```
-keep public class com.nhn.android.naverlogin.** {
       public protected *;
}
```



