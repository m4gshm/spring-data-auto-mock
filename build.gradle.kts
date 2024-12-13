plugins {
    `java-library`
    `maven-publish`
}

group = "github.m4gshm"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations.annotationProcessor {
    extendsFrom(configurations.compileOnly.get())
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")

    implementation("org.slf4j:slf4j-api:2.0.9")

    val springVer = "5.3.16"
    val springBootVer = "2.7.18"
    val mockitoVer = "4.0.0"
    implementation("org.mockito:mockito-core:$mockitoVer")
    implementation("org.springframework.data:spring-data-commons:$springBootVer")
    implementation("org.springframework:spring-context:$springVer")
    implementation("org.springframework.boot:spring-boot-starter-test:$springBootVer")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(true)
}

publishing {
    publications {
        create<MavenPublication>("java") {
            pom {
                description.set("todo")
                url.set("https://github.com/m4gshm/spring-data-mock")
                properties.put("maven.compiler.target", "${java.targetCompatibility}")
                properties.put("maven.compiler.source", "${java.sourceCompatibility}")
                developers {
                    developer {
                        id.set("m4gshm")
                        name.set("Bulgakov Alexander")
                        email.set("mfourgeneralsherman@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/m4gshm/spring-data-mock.git")
                    developerConnection.set("scm:git:https://github.com/m4gshm/spring-data-mock.git")
                    url.set("https://github.com/m4gshm/spring-data-mock")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/m4gshm/spring-data-mock?tab=MIT-1-ov-file#readme")
                    }
                }
            }
            from(components["java"])
        }
    }
    repositories {
        maven("file://$rootDir/../m4gshm.github.io/maven2") {
            name = "GithubMavenRepo"
        }
    }
}