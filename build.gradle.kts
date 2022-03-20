// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.4")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    val navVersion = "2.3.5"
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

tasks.register("clean",Delete::class){
  delete(rootProject.buildDir)
}