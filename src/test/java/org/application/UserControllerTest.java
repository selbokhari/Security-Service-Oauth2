package org.application;

import org.application.service.AuthentificationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // pour que la méthode @BeforeAll ne soit pas static
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // on utilise SpringBootTest.WebEnvironment.DEFINED_PORT si on veut utiliser le port ${server.port}
class UserControllerTest {

    private static final String USER_URI = "http://localhost:{0}/user/{1}";
    private static final String BEARER_TOKEN = "Bearer {0}";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final Map<String, String> userCredentiels = Map.of(USERNAME, "user", PASSWORD, "user");
    private static final Map<String, String> adminCredentiels = Map.of(USERNAME, "admin", PASSWORD, "admin");

    @Autowired
    AuthentificationService authentificationService;

    @LocalServerPort
    private int port; // le port aléatoire, on a utilisé un port aléatoire pour éviter la collision avec le port actuel 8084

    private HttpClient httpClient;
    private String userAccessToken;
    private String adminAccessToken;

    @BeforeAll
    public void initAccessToken() {
        httpClient = HttpClient.newHttpClient();
        userAccessToken = authentificationService.authentifier(userCredentiels.get(USERNAME), userCredentiels.get(PASSWORD)).get(ACCESS_TOKEN);
        adminAccessToken = authentificationService.authentifier(adminCredentiels.get(USERNAME), adminCredentiels.get(PASSWORD)).get(ACCESS_TOKEN);
    }

    @Test
    void supprimerUtilisateurTestETQUtilisateur() throws IOException, InterruptedException {
        HttpResponse<String> responseUser = effectuerAppelRest(userAccessToken);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), responseUser.statusCode());
    }

    @Test
    void supprimerUtilisateurTestETQAdmin() throws IOException, InterruptedException {
        HttpResponse<String> responseAdmin = effectuerAppelRest(adminAccessToken);
        Assertions.assertEquals(HttpStatus.OK.value(), responseAdmin.statusCode());
    }

    private HttpResponse<String> effectuerAppelRest(String jwtToken) throws IOException, InterruptedException {
        String uri = MessageFormat.format(USER_URI, String.valueOf(port), 1l);
        HttpRequest userRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(AUTHORIZATION_HEADER, MessageFormat.format(BEARER_TOKEN, jwtToken))
                .DELETE()
                .build();

        return httpClient.send(userRequest, HttpResponse.BodyHandlers.ofString());
    }
}
