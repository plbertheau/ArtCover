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

# Keep the main application class
-keep class com.plbertheau.** { *; }

# Prevent stripping annotations (needed for Retrofit, Dagger, etc.)
-keepattributes *Annotation*

# Ne pas supprimer les classes ou méthodes avec les annotations spécifiques utilisées par le framework
-keep class com.plbertheau.data.* { *; }
-keep class com.plbertheau.domain.* { *; }

# Keep Retrofit API interfaces
-keep interface com.plbertheau.api.** { *; }

# Keep Hilt classes
-keep class hilt.** { *; }

# Remove debug info in Release builds
-dontwarn com.plbertheau.debug.**