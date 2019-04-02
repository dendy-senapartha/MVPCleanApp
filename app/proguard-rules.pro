# Add project specific ProGuard rules here.
#---------------------------------basic code----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
#-dontwarn
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#keep used annotation
-keepclassmembers class * {
    @com.alipay.mobile.framework.service.annotation.SignCheck *;
    @com.alipay.mobile.framework.service.annotation.OperationType *;
    @com.alipay.mobile.framework.service.annotation.CheckLogin *;
    @com.alipay.mobile.framework.service.annotation.ResetCookie *;
    @com.alipay.mobile.framework.service.annotation.UpdateDeviceInfo *;
    @org.greenrobot.eventbus.Subscribe *;
}

# --------------------------------------------------------------------
# STRIP all Log messages
# --------------------------------------------------------------------
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

#---------------------------------COMMON ANDROID LIBS----------------------------------
# Dagger ProGuard rules.
# https://github.com/square/dagger
-dontwarn dagger.internal.codegen.**

-keepclassmembers class dagger.* { *; }
-keepclassmembers class javax.inject.* { *; }
-keepclassmembers class * extends dagger.internal.Binding
-keepclassmembers class * extends dagger.internal.ModuleAdapter
-keepclassmembers class * extends dagger.internal.StaticInjection

# butterknife rules
-keepclassmembers public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keepclassmembers class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

#As the experts said, there is no need to bofuscate RXJAVA 2.x.

#zxing need to be optimize
#-keep class com.google.zxing.**{*;}

-keepclassmembers class de.hdodenhof.circleimageview.**{*;}

### Glide, Glide Okttp Module, Glide Transformations
-keepclassmembers public class * implements com.bumptech.glide.module.GlideModule
-keepclassmembers public class * extends com.bumptech.glide.module.AppGlideModule
-keepclassmembers public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keepclassmembers class com.caverock.androidsvg.**{*;}
-keepclassmembers class com.jakewharton.rxbinding2.**{*;}
-keepclassmembers class com.wajahatkarim3.easyflipview.**{*;}
-keepclassmembers class com.zhuinden.simplestack.**{*;}

#-keep class com.google.auto.value.**{*;}
-keepclassmembers class net.cachapa.expandablelayout.**{*;}
-keepclassmembers class me.grantland.widget.**{*;}
-keepclassmembers class com.synnapps.carouselview.**{*;}
-keepclassmembers class io.michaelrocks.libphonenumber.android.**{*;}
-keepclassmembers class com.f2prateek.rx.preferences2.**{*;}
-keepclassmembers class com.afollestad.materialdialogs.**{*;}

-keepclassmembers class org.greenrobot.eventbus.**{*;}
-keepclassmembers class io.hypertrack.smart_scheduler.**{*;}
-keepclassmembers class com.appsflyer.**{*;}

#---------------------------------LOCAL KIT-----------------------------------


#---------------------------------third party ----------------------------------
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.alibaba.fastjson.**{*;}
-keep class com.alipay.**{*;}
-keep class org.json.alipay.**{*;}
-keep class com.uc.**{*;}
-keep class com.j256.**{*;}
-keep class com.android.internal.**{*;}
-keep class com.ta.**{*;}
-keep class com.ut.**{*;}

#-----------AMCS-----------
# base
-keepclassmembers class * implements java.io.Serializable {*;}

# UTDevice
-keep class com.ta.utdid2.device.UTDevice {*;}

# Sync
-keep class com.alipay.mobile.rome.** {*;}

# Security Guard
-keep class com.taobao.securityjni.** {*;}
-keep class com.taobao.wireless.security.** {*;}
-keep class com.ut.secbody.**{*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.securityguard.securitybody.**{*;}
-keep class com.taobao.accs.**{*;}
-keep class com.ut.mini.**{*;}

# aplog
-keep class com.alipay.iap.android.aplog.api.*{public *;}
-keep class com.alipay.iap.android.aplog.core.**{public *;}
-keep class com.alipay.iap.android.aplog.util.**{public *;}
-keep class com.alipay.iap.android.aplog.misc.**{public *;}
-keep class com.alipay.iap.android.aplog.log.**{*;}
-keep class com.uc.**{*;}
-keep class com.ta.**{*;}
-keep class com.ut.**{*;}
-keep class com.alipay.iap.android.aplog.core.logger.CrashLogBinder{*;}

#  zoloz
-keep class com.alipay.**{*;}
-keep class com.ap.**{*;}
-keep class com.zoloz.**{*;}
-keep class zoloz.ap.**{*;}

#-----------apsecurity-----------
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.alipay.alipaysecuritysdk.apdid.face.APSecuritySdk {
    public <fields>;
    public <methods>;
}

-keep class com.alipay.alipaysecuritysdk.apdid.face.APSecuritySdk$* { *;}

-keep class com.alipay.alipaysecuritysdk.apdid.face.DeviceTokenClient {
    public <fields>;
    public <methods>;
}

-keep class com.alipay.alipaysecuritysdk.apdid.face.DeviceTokenClient$* { *;}

-keep class com.alipay.alipaysecuritysdk.rds.face.RDSInfoSDK {
    public <fields>;
    public <methods>;
}
-keep class com.alipay.alipaysecuritysdk.rds.face.RDSInfoSDK { *;}

-keep class com.alipay.alipaysecuritysdk.rds.constant.CONST {
    public <fields>;
    public <methods>;
}
-keep class com.alipay.alipaysecuritysdk.rds.constant.DictionaryKeys {
    public <fields>;
    public <methods>;
}
-keep class com.alipay.alipaysecuritysdk.rds.constant$* { *;}

-keep class com.alipay.alipaysecuritysdk.rds.v2.face.RDSClient {
    public <fields>;
    public <methods>;
}

-keep class com.alipay.alipaysecuritysdk.rds.v2.face.RDSClient$* { *;}


-keep class com.alipay.alipaysecuritysdk.attack.** {*;}
-keep class com.alipay.alipaysecuritysdk.attack.common.PackHookPlugin$* {*;}


-keep class com.alipay.mobile.security.senative.APSE$* { *;}

-keep class com.alipay.alipaysecuritysdk.rds.v2.face.RDSClient$* { *;}
-keep class com.alipay.alipaysecuritysdk.attack.** {*;}
-keep class com.alipay.alipaysecuritysdk.attack.common.PackHookPlugin$* {*;}
-keep class com.alipay.mobile.security.senative.APSE$* { *;}
-keep class com.alipay.a.a.a.a.**{*;}
-keep class com.alipay.android.phone.**{*;}
-keep class com.alipay.alipaysecuritysdk.**{*;}
-keep class com.alipay.apmobilesecuritysdk.**{*;}
-keep class com.alipay.b.**{*;}
-keep class com.alipay.c.a.a.**{*;}
-keep class com.alipay.mobile.framework.service.annotation.**{*;}
-keep class com.alipay.tscenter.biz.rpc.**{*;}

-dontwarn com.taobao.dp.**
-dontwarn com.alibaba.wireless.security.**
-dontwarn com.alibaba.fastjson.**
-dontwarn com.alipay.**
-dontwarn org.json.alipay.**
-dontwarn com.uc.**
-dontwarn com.j256.**
-dontwarn com.ta.**
-dontwarn com.ut.**
-dontwarn com.google.auto.value.**
-dontwarn com.ryanharter.auto.value.**

# lifecycle
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}
