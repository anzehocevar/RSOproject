package si.fri.rso.skupina06.prijava.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final JwtDecoder jwtDecoder;

    private Jwt loggedInUserToken = null;

    public UserController(@Qualifier("jwtDecoder") JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Operation(
            summary = "Pridobi prijavljenega uporabnika na podlagi JWT",
            description = "Pridobi json objekt uporabnika na podlagi JWT",
            responses = {
                    @ApiResponse(
                            description = "User found",
                            responseCode = "200"
                    )
            }
    )
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


    @Operation(
            summary = "Nastavi JWT prijavljenega uporabnika",
            description = "Nastavi JWT prijavljenega uporabnika za pridobivanje informacij o prijavljenem uporabniku.",
            responses = {
                    @ApiResponse(
                            description = "Token saved",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Token is null",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/set-token")
    public ResponseEntity<Void> setUserToken(@RequestBody Map<String, String> body) {
        logger.info(body.toString());
        String token = body.get("token");
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Setting logged in user token {}", token);
        try {
            loggedInUserToken = jwtDecoder.decode(token);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Pridobi prijavljenega uporabnika",
            description = "Pridobi informacije prijavljenega uporabnika glede na shranjen JWT prijavljenega uporabnika.",
            responses = {
                    @ApiResponse(
                            description = "Got user info",
                            responseCode = "200"
                    )
            }
    )
    /**
     * Get logged in user data based on the JWT token of the currently logged in user.
     */
    @GetMapping("/logged-in-user")
    public ResponseEntity<Map<String, Object>> getLoggedUserInfo() {
        logger.info("Getting logged in user info {}", loggedInUserToken);
        return getUserInfo(loggedInUserToken);

    }

    @Operation(
            summary = "Končna točka za role anotherRole",
            description = "Končna točka do katere dostopajo lahko le uporabniki z vlogo 'anotherRole'"
    )
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

}
