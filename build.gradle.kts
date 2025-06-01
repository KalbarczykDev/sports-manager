plugins {
    id("java")
    id("application")
    id("java-library")
}

group = "mas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(24))
}

tasks.test {
    useJUnitPlatform()
   
}

application {
    mainClass.set("mas.Main")
}
