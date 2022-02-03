plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")      version "1.5.31"
    id("io.gitlab.arturbosch.detekt")   version "1.19.0"
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
    val arrowVersion = "1.0.1"
    implementation(platform("io.arrow-kt:arrow-stack:$arrowVersion"))
    implementation("io.arrow-kt:arrow-core")

    // Ktor
    val ktorVersion = "1.6.7"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logging
    val logbackVersion = "1.2.10"
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Testing
    val kotestVersion = "5.1.0"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget           = "11"
        freeCompilerArgs    = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}
detekt {
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
