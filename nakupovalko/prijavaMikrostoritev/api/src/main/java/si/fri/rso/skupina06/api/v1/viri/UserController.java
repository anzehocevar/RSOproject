package si.fri.rso.skupina06.api.v1.viri;

import com.nimbusds.oauth2.sdk.TokenRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    private Jwt loggedInUserToken = null;

    public UserController(@Qualifier("jwtDecoder") JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Get user information from JWT
     */
    @CircuitBreaker(name = "getUserInfo", fallbackMethod = "fallbackGetUserInfo")
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

    public ResponseEntity<Map<String, Object>> fallbackGetUserInfo(@AuthenticationPrincipal Jwt jwt, Throwable t) {
        logger.error("Circuit breaker activated for user-info: {}", t.getMessage());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", "");
        userInfo.put("name", null);
        userInfo.put("email", null);
        userInfo.put("roles", null);

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

    /**
     * Get logged in user data based on the JWT token of the currently logged in user.
     */
    @GetMapping("/logged-in-user")
    public ResponseEntity<Map<String, Object>> getLoggedUserInfo() {
        logger.info("Getting logged in user info {}", loggedInUserToken);
        return getUserInfo(loggedInUserToken);

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
