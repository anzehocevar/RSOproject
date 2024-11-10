package si.fri.rso.skupina06.api.v1.viri;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * Get user information from JWT
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", jwt.getSubject());
        userInfo.put("name", jwt.getClaim("name"));
        userInfo.put("email", jwt.getClaim("email"));
        userInfo.put("roles", jwt.getClaim("roles"));

        return ResponseEntity.ok(userInfo);
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
