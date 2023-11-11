package org.example.jacoco.repro;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Keycloak Integration Test
 * <p>
 * this test allows the developer to run the code as a black box inside the container,
 * and use junit as a hollow shell/test runner.
 * <p>
 * Many issues with this approach, but we can start with code coverage
 * <p>
 * First, we bring jacoco into the container by a file system bind
 * <p>
 * below, we have looked at the entrypoint for the KeycloakContainer's default image:
 * {@code quay.io/keycloak/keycloak:22.0.5}, and found {@code JAVA_OPTS_APPEND}
 * to be a way to configure jacoco inside the container.
 * <p>
 * however, we still have to figure out how to get the execfile out of the container,
 * and into the correct location. the commands in the pom result in the .exec file
 * being generated and saved to {@code target/jacoco.exec}, which is not where
 * it seems like it would be saved with the below configuration. 
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeycloakIntegrationTest {

    static KeycloakContainer keycloakContainer = new KeycloakContainer()
            // .withNetwork(network)
            // .withRealmImportFile("realm-export.json")
            .withProviderClassesFrom("target/classes")
            // .withReuse(true)
            .withFileSystemBind("./target/jacoco-agent", "/jacoco-agent")
            .withFileSystemBind("./target/jacoco-report", "/jacoco-report")
            .withEnv("JAVA_OPTS_APPEND",
                    "-javaagent:/jacoco-agent/org.jacoco.agent-runtime.jar=destfile=/jacoco-report/jacoco.exec")
            ;

    @BeforeAll
    static void setUpKeycloakContainer() {
        keycloakContainer.start();
    }

    @Test
    void test() {
        System.out.println("authServerUrl: " + keycloakContainer.getAuthServerUrl());
    }
}
