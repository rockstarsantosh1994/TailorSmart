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
-keep class javax.annotation.* { *; }
-keepclassmembers class javax.annotation.* { *; }
-dontwarn com.facebook.android.BuildConfig
#-keep class android.app.**
-dontwarn rx.**

#-dontwarn okio.**

#-dontwarn com.squareup.okhttp.*

-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

-dontwarn retrofit.appengine.UrlFetchClient

-dontwarn retrofit.client.**


-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.retrofit.**
-keep class **$$ViewBinder { *; }
-dontwarn android.util.FloatMath
-dontwarn retrofit2.Platform$Java8

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-dontnote okhttp3.**, okio.**, retrofit2.**
-ignorewarnings

-keep class com.bma.aco.placesautocomplete.**{*;}
-keep public interface com.bma.aco.placesautocomplete.history.AutocompleteHistoryManager{*;}

# don't process support library
# Hide warnings about references to newer platforms in the library
-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#To maintain custom components names that are used on layouts XML:
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#To keep parcelable classes (to serialize - deserialize objects to sent through Intents)
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

##### Citrus Core SDK Config starts here....#########
-keep class com.citrus.** { *; }
-keep class com.citruspay.citrusbrowser.** { *; }
-keep class com.google.** { *; }
-keep class com.zl.reik.** { *; }
-keepattributes *Annotation*
###############   END ....###########################

############################# Begin: proguard configuration for Gson  ##########################
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class com.google.gson.stream.** { *; }
-keep class com.google.** {*;}

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-assumenosideeffects class android.util.Log {
 public static *** d(...);
 public static *** i(...);
 public static *** v(...);
}

#This is Android Shared preference wrapper that encrypts the values of Shared Preferences using AES 128, CBC, and PKCS5 padding with integrity checking in the form of a SHA 256 hash.
-keep class com.tozny.crypto.android.AesCbcWithIntegrity$PrngFixes$* { *; }
############################# End: proguard configuration for Gson  ############################

# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.praxello.tailorsmart.model.** {
*;
}