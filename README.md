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

Google Login need firebase  
connect https://console.firebase.google.com/  
create firebase project and add android  
next, Authentication -> Sign-in-method -> Google use
    
first write Android App PackageName, NickName, SHA-1  
    
### How to get SHA-1
    Android Studio -> Gradle(Android studio right) -> My Application -> Task -> Android -> signingReport  
    
next, download google-services.json, your android project app folder input  

build.gradle(project: AppName)
<pre>
<code>
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
</code>
</pre>

builde.gradle(Module: app)
<pre>
<code>
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
</code>
</pre>

after, Sync Now

## step2 Kakao Login

## step3 Naver Login
 
