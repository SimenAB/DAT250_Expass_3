plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "no.hvl.dat250"
version = "0.0.1-SNAPSHOT"
description = "Expass 2"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // Hibernate - H2
    implementation("org.hibernate.orm:hibernate-core:7.1.1.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("com.h2database:h2:2.3.232")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
