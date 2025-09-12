plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "nqt"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot Core
	implementation("org.springframework.boot:spring-boot-starter:_")
	implementation("org.springframework.boot:spring-boot-starter-web:_")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")
	implementation("org.springframework.boot:spring-boot-starter-security:_")
	implementation("org.springframework.boot:spring-boot-starter-validation:_")
	implementation("org.springframework.boot:spring-boot-starter-websocket:_")
	implementation("org.springframework.boot:spring-boot-starter-security:_")
	implementation("org.springframework.boot:spring-boot-starter-actuator:_")
	implementation("org.springframework:spring-messaging:_")
	implementation("org.springframework:spring-websocket:_")

	// Database
	runtimeOnly("org.postgresql:postgresql:_")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:_")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:_")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:_")

	// Swagger / OpenAPI
	implementation("org.apache.commons:commons-lang3:3.18.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")

	// Lombok
	compileOnly("org.projectlombok:lombok:_")
	annotationProcessor("org.projectlombok:lombok:_")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test:_")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:_")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
