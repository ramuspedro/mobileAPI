import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"

	id("io.gitlab.arturbosch.detekt").version("1.10.0")
	id("name.remal.check-dependency-updates").version("1.0.199")
}

group = "com.cvillaseca"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.0.RELEASE")
	implementation("com.zaxxer:HikariCP:3.4.5")
	implementation("mysql:mysql-connector-java:8.0.16")

	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "mockito-core")
	}
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("com.ninja-squad:springmockk:2.0.2")

	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.10.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

detekt {
	toolVersion = "1.9.1"
	config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
	baseline = file("${rootProject.projectDir}/config/detekt/baseline.xml")
	buildUponDefaultConfig = true
}
