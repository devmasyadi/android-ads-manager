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

#Tapdaq
-keep class com.tapdaq.sdk.** { *; }
-keep class com.tapdaq.adapters.* { *; }
-keep class com.tapdaq.unityplugin.* { *; }
-keep class com.google.android.gms.ads.identifier.** { *; }

#Admob
-keep public class com.google.android.gms.ads.** {
    public *;
}

-keep public class com.google.ads.** {
    public *;
}

#Applovin
-dontwarn com.applovin.**
-keep class com.applovin.** { *; }

#FAN
-keep class com.facebook.ads.** { *; }

#UnityAds
# Keep filenames and line numbers for stack traces
-keepattributes SourceFile,LineNumberTable

# Keep JavascriptInterface for WebView bridge
-keepattributes JavascriptInterface

# Sometimes keepattributes is not enough to keep annotations
-keep class android.webkit.JavascriptInterface {
   *;
}

# Keep all classes in Unity Ads package
-keep class com.unity3d.ads.** {
   *;
}

# Keep all classes in Unity Services package
-keep class com.unity3d.services.** {
   *;
}
-dontwarn com.unity3d.services.**