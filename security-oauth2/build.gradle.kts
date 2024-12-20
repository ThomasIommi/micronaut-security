plugins {
    id("io.micronaut.build.internal.security-module")
}

dependencies {
    annotationProcessor(mn.micronaut.graal)
    annotationProcessor(mnSerde.micronaut.serde.processor)
    annotationProcessor(projects.micronautSecurityAnnotations)
    annotationProcessor(mnValidation.micronaut.validation.processor)
    api(mnValidation.validation) //  // jakarta.validation:jakarta.validation-api
    testImplementation(mnValidation.micronaut.validation)
    compileOnly(mn.micronaut.inject.java)
    compileOnly(projects.micronautSecurityJwt)
    compileOnly(mn.micronaut.http.server)
    api(projects.micronautSecurity)
    implementation(mn.micronaut.http.client.core)
    compileOnly(mn.jackson.databind)
    compileOnly(mnSession.micronaut.session)
    implementation(mnReactor.micronaut.reactor)
    testImplementation(mnSerde.micronaut.serde.jackson)
    testImplementation(projects.micronautSecuritySession)
    testImplementation(mn.micronaut.http.client)
    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mnTestResources.testcontainers.core)
    testImplementation(mn.groovy.json)
    testImplementation(projects.micronautSecurityJwt)
    testImplementation(projects.testSuiteUtils)
    testImplementation(projects.testSuiteUtilsSecurity)
    testImplementation(projects.testSuiteKeycloakDocker)
    testImplementation(mnLogging.logback.classic)
    testImplementation(libs.system.stubs.core)
    testImplementation(mn.micronaut.retry)

    testAnnotationProcessor(mn.micronaut.inject.java)
    testImplementation(mnTest.micronaut.test.junit5)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(platform(mnTest.boms.junit))
    testImplementation(libs.junit.jupiter.params)
}
tasks.withType<Test> {
    useJUnitPlatform()
}
