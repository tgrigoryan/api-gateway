import org.jetbrains.kotlin.js.backend.ast.JsEmpty.setSource

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")      version "1.6.21"
    id("io.gitlab.arturbosch.detekt")   version "1.22.0-RC3"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Arrow
    val arrowVersion = "1.1.3"
    implementation(platform("io.arrow-kt:arrow-stack:$arrowVersion"))
    implementation("io.arrow-kt:arrow-core")

    // Ktor
    val ktorVersion = "1.6.8"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logging
    val logbackVersion = "1.3.4"
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Testing
    val kotestVersion = "5.5.4"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    // Detekt
    val detektFormattingVersion = "1.22.0-RC3"
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")
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
    config                  = files("$projectDir/config/detekt.yml")
    // baseline             = file("$projectDir/config/baseline.xml")
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
