# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-ignorewarnings
#-dontshrink
#-dontoptimize
#-dontobfuscate
-dontskipnonpubliclibraryclasses

-renamesourcefileattribute SourceFile

#-keepattributes SourceFile,LineNumberTable,EnclosingMethod,*Annotation*
-keepattributes *Annotation*

-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }

#-keep class com.ushaqi.zhuishushenqi.**
#-keepclassmembers class com.ushaqi.zhuishushenqi.** { public <init>(...); }
#-keepclassmembers class com.ushaqi.zhuishushenqi.** {
#    *** set*(***);
#    *** get*();
#}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.os.BinderProxy
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View { public <init>(android.content.Context); public <init>(android.content.Context, android.util.AttributeSet); public <init>(android.content.Context, android.util.AttributeSet, int); public void set*(...); }
-keep public class * extends android.preference.Preference { *; }
-keep public class * extends android.preference.Preference


-keepclassmembers class * extends android.app.Activity { public void *(android.view.View); }




-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}



# -- Otto --
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}





# -- Gson --
-keep class com.google.gson.** { *; }


# removes such information by default, so configure it to keep all of it.
-keepattributes Signature



# -- Umeng --
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep class com.uc.** {*;}
-keep class com.zui.** {*;}
-keep class com.miui.** {*;}
-keep class com.heytap.** {*;}
-keep class a.** {*;}
-keep class com.vivo.** {*;}

-keep public class com.ushaqi.zhuishushenqi.R$*{
public static final int *;
}
-keep class com.umeng.** {*;}


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.getui.**
-keep class com.getui.**{*;}




-keepattributes SourceFile,LineNumberTable

-keep class **.R$* {
*;
}



-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}


##阿里云HttpDns
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}



-keepclassmembers class * {
    native <methods>;
}


#tablayout 样式
-keep class com.google.android.material.tabs.TabLayout{*;}
#jia add, upgrade sdk26 and permission manage, org.apache.http
-keep class org.apache.http.** {*;}

#bugly 避免混淆
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keep class com.ushaqi.zhuishushenqi.httpcore.api.**{ *; }

# okhttp
-dontwarn okio.**

-keep class com.ushaqi.zhuishushenqi.model.** { *; }


-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.bun.lib.**{*;}
-keep class com.bun.miitmdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class com.zui.opendeviceidlibrary.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
#百川
#-keepattributes Signature
#-ignorewarnings
#-keep class javax.ws.rs.** { *; }
#-keep class com.alibaba.fastjson.** { *; }
#-dontwarn com.alibaba.fastjson.**
#-keep class sun.misc.Unsafe { *; }
#-dontwarn sun.misc.**
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#-keep class com.ut.** {*;}
#-dontwarn com.ut.**
#-keep class com.ta.** {*;}
#-dontwarn com.ta.**
#-keep class org.json.** {*;}
#-keep class com.ali.auth.**  {*;}
#-dontwarn com.ali.auth.**
#-keep class com.taobao.securityjni.** {*;}
#-keep class com.taobao.wireless.security.** {*;}
#-keep class com.taobao.dp.**{*;}
#-keep class com.alibaba.wireless.security.**{*;}
#-keep interface mtopsdk.mtop.global.init.IMtopInitTask {*;}
#-keep class * implements mtopsdk.mtop.global.init.IMtopInitTask {*;}
-keepattributes Signature
-ignorewarnings
-keep class javax.ws.rs.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.alibaba.fastjson.**
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class org.json.** {*;}

-keep class tv.danmaku.ijk.media.player.TaobaoMediaPlayer{*;}
-keep class tv.danmaku.ijk.media.player.TaobaoMediaPlayer$*{*;}
#京东配置
-keep class com.kepler.jd.**{ public <fields>; public <methods>; public *; }
#拼多多
-keep class com.xunmeng.**{*;}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}