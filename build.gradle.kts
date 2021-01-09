plugins {
    kotlin("jvm") version "1.3.71"
}

group = "com.kyleriedemann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-runtime"))
    implementation("com.github.holgerbrandl:kscript-annotations:1.2")
    implementation("com.andreapivetta.kolor:kolor:1.0.0")
    implementation("com.github.ajalt:clikt:2.6.0")

    testImplementation("junit:junit:4.13")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.22")
}