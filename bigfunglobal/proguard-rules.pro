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
#-optimizationpasses 5
## 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
#-dontusemixedcaseclassnames
## 指定不去忽略非公共的库类(不跳过library中的非public的类)
#-dontskipnonpubliclibraryclasses
## 指定不去忽略包可见的库类的成员
#-dontskipnonpubliclibraryclassmembers
##不进行优化，建议使用此选项，
#-dontoptimize
# # 不进行预校验,Android不需要,可加快混淆速度。
#-dontpreverify
## 屏蔽警告
#-ignorewarnings
## 指定混淆是采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不做更改
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
## 保护代码中的Annotation不被混淆
#-keepattributes *Annotation*
## 避免混淆泛型, 这在JSON实体映射时非常重要
#-keepattributes Signature
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
# #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
## 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
## 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
##指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
#-allowaccessmodification
##当有优化和使用-repackageclasses时才适用。
#-repackageclasses ''
# # 混淆时记录日志(打印混淆的详细信息)
# # 这句话能够使我们的项目混淆后产生映射文件
# # 包含有类名->混淆后类名的映射关系
#-verbose
-keep class com.adjust.sdk.** { *; }
-keep class com.ironsource.mediationsdk.** { *; }
-keep class com.tendcloud.tenddata.** { *; }
-keep class com.android.billingclient.**{*;}
-keep public class com.android.installreferrer.** { *; }
-keep class okhttp3.**{*;}
-keep final class okhttp3.**{*;}
-keep public class * extends android.app.Activity

-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

-dontwarn okhttp3.**

-dontwarn okio.**
-keep class okio.**{*;}
-keep class com.goldsource.sdk.**{*;}

-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

-dontwarn com.paytm.pgsdk.**
-keep class com.paytm.pgsdk.** {*;}

-keepclassmembers class **.R$* {
  *;
}

-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}


-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-dontwarn com.facebook.ads.internal.**
-keeppackagenames com.facebook.*
-keep public class com.facebook.ads.** {*;}
-keep public class com.facebook.ads.**
{ public protected *; }
# For communication with AdColony's WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# Keep ADCNative class members unobfuscated
-keepclassmembers class com.adcolony.sdk.ADCNative** {
    *;
 }
 -keepattributes Signature,InnerClasses,Exceptions,Annotation
 -keep public class com.applovin.sdk.AppLovinSdk{ *; }
 -keep public class com.applovin.sdk.AppLovin* { public protected *; }
 -keep public class com.applovin.nativeAds.AppLovin* { public protected *; }
 -keep public class com.applovin.adview.* { public protected *; }
 -keep public class com.applovin.mediation.* { public protected *; }
 -keep public class com.applovin.mediation.ads.* { public protected *; }
 -keep public class com.applovin.impl.*.AppLovin { public protected *; }
 -keep public class com.applovin.impl.**.*Impl { public protected *; }
 -keepclassmembers class com.applovin.sdk.AppLovinSdkSettings { private java.util.Map localSettings; }
 -keep class com.applovin.mediation.adapters.** { *; }
 -keep class com.applovin.mediation.adapter.**{ *; }

#<基础>
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.** {*;}


-keep class com.bigfun.sdk.**{ *; }

-keep enum *{
 *;
}
-keep class *{
 *;
}
-keep interface *{
 *;
}



#-keepclassmembers class com.bigfun.sdk.BigFunSDK{
#public static void init(*);
#public static void getSwitch(*);
#public static void PlCFEe(*);
#}

-optimizationpasses 5
# 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses
# 指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不进行优化，建议使用此选项，
-dontoptimize
 # 不进行预校验,Android不需要,可加快混淆速度。
-dontpreverify
# 屏蔽警告
-ignorewarnings
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
# 避免混淆泛型, 这在JSON实体映射时非常重要
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
-allowaccessmodification
#当有优化和使用-repackageclasses时才适用。
-repackageclasses ''
 # 混淆时记录日志(打印混淆的详细信息)
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
-verbose

