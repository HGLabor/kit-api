val javaVersion = 17
val minecraftVersion = "1.19.2"
val repo = "HGLabor/kit-api"

group = "de.hglabor"
version = "1.0.0"

plugins {
    java
    `maven-publish`
    signing
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${minecraftVersion}-R0.1-SNAPSHOT")}

tasks {
    withType<JavaCompile> {
        options.release.set(javaVersion)
        options.encoding = "UTF-8"
    }
}

signing {
    sign(publishing.publications)
}

publishing {
    kotlin.runCatching {
        repositories {
            maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                name = "ossrh"
                credentials(PasswordCredentials::class) {
                    username = (property("ossrhUsername") ?: return@credentials) as String
                    password = (property("ossrhPassword") ?: return@credentials) as String
                }
            }
        }
    }.onFailure {
        println("Unable to add publishing repositories: ${it.message}")
    }


    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            //artifact(tasks.jar.get().outputs.files.single())

            this.groupId = project.group.toString()
            this.artifactId = project.name.toLowerCase()
            this.version = project.version.toString()

            pom {
                name.set(project.name)
                description.set(project.description)

                developers {
                    developer {
                        name.set("copyandexecute")
                    }
                    developer {
                        name.set("mooziii")
                    }
                }

                licenses {
                    license {
                        name.set("GNU General Public License, Version 3")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    }
                }

                url.set("https://github.com/${repo}")

                scm {
                    connection.set("scm:git:git://github.com/${repo}.git")
                    url.set("https://github.com/${repo}/tree/main")
                }
            }
        }
    }
}
