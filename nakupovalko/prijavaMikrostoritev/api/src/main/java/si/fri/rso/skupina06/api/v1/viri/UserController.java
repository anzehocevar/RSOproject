package si.fri.rso.skupina06.api.v1.viri;

import com.nimbusds.oauth2.sdk.TokenRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final JwtDecoder jwtDecoder;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.client.registration.keycloak.client-secret}")
    private String clientSecret;

    private Jwt loggedInUserToken = null;

    public UserController(@Qualifier("jwtDecoder") JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Get user information from JWT
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        logger.info("Getting logged in user info");

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", jwt.getSubject());
        userInfo.put("name", jwt.getClaim("name"));
        userInfo.put("email", jwt.getClaim("email"));
        userInfo.put("roles", jwt.getClaim("roles"));

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/set-token")
    public ResponseEntity<Void> setUserToken(@RequestBody Map<String, String> body) {
        logger.info(body.toString());
        String token = body.get("token");
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Setting logged in user token {}", token);
        loggedInUserToken = jwtDecoder.decode(token);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/logged-in-user")
    public ResponseEntity<Map<String, Object>> getLoggedUserInfo() throws IOException, InterruptedException {
        logger.info("Getting logged in user info {}", loggedInUserToken);
        return getUserInfo(loggedInUserToken);

    }






    @GetMapping("/token")
    public String getToken() {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = authentication.getToken();
        String tokenValue = jwt.getTokenValue();

        return tokenValue;
    }

    @GetMapping("/logged-in-user2")
    public ResponseEntity<Map<String, Object>> getLoggedUserInfo2() throws IOException, InterruptedException {
        String url = issuerUri.concat("/protocol/openid-connect/token");
        logger.info("Attempting to connect to: {}", url);
        logger.info("Attempting to connect to: {}", issuerUri);

        //String formData = "grant_type=client_credentials&client_id=".concat(clientId).concat("&client_secret=").concat(clientSecret);

        String formData = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s",
                            URLEncoder.encode(clientId, StandardCharsets.UTF_8),
                            URLEncoder.encode(clientSecret, StandardCharsets.UTF_8));
        logger.info("Grant type: {}", formData);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .timeout(Duration.ofSeconds(10))
                .build();

        // Get JWT
        response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if(response == null) {
            return null;
        }

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            JSONObject jsonResponse = new JSONObject(responseBody);
            String accessToken = jsonResponse.getString("access_token");
            logger.info("Access token: {}", accessToken);

            return getUserInfo(jwtDecoder.decode(accessToken));

        } else {
            logger.error("Failed to get JWT of logged in user: {}", response.statusCode());
        }
        return null;
    }

    /**
     * Requires USER role to access
     */
    @GetMapping("/protected")
    @PreAuthorize("hasRole('anotherRole')")
    /*public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("This is a protected endpoint");
    }*/
    public String protectedEndpoint() {
        return "protected";
    }

    /**
     * Requires ADMIN role to access
     */
    @GetMapping("/admin2")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This is an admin endpoint");
    }
}
