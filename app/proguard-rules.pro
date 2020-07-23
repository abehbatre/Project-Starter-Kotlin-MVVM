# Common Proguard Rule
# -----------------------------------------------
# @created by   : Aditya Pratama
# @date         : 2019


# global rule
-keepattributes Signature
-keepattributes *Annotation*
-optimizationpasses 5
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keep class id.web.adit.starter.datasource.** { *; }
# AAC ---
-keep class * extends androidx.lifecycle.ViewModel { <init>(); }
-keep class * extends androidx.lifecycle.AndroidViewModel { <init>(android.app.Application); }

# This is for Kotlin
-dontwarn kotlin.**
-keepnames class kotlinx.** { *; }



-keep class com.github.mikephil.charting.** { *; }
-dontwarn io.realm.**


# For All HTTP Library
-dontwarn okio.**
-keep class okhttp3.* {
* ; } -keep interface okhttp3.* {
* ; } -dontwarn okhttp3.

-keep class com.google.gson.** { *; }
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn sun.misc.**




-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep public class * extends android.app.Fragment

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

-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}


