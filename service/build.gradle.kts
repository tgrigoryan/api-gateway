import org.jetbrains.kotlin.js.backend.ast.JsEmpty.setSource

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")      version "1.9.21"
    id("io.gitlab.arturbosch.detekt")   version "1.23.4"
    application
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Arrow
    val arrowVersion = "1.2.1"
//    implementation(platform("io.arrow-kt:arrow-stack-jvm:$arrowVersion"))
    implementation("io.arrow-kt:arrow-core-jvm:$arrowVersion")

    // Ktor
    val ktorVersion = "2.3.6"
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    // Logging
    val logbackVersion = "1.3.4"
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Testing
    val kotestVersion = "5.8.0"
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")

    // Detekt
    val detektFormattingVersion = "1.23.4"
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget           = "11"
        freeCompilerArgs    = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}
detekt {
    setSource(files(projectDir))
    autoCorrect             = true
    parallel                = true
    disableDefaultRuleSets  = true
    buildUponDefaultConfig  = true
    allRules                = false
    config.setFrom(files("$projectDir/config/detekt.yml"))

}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("tg.agw.ServiceKt")
}
