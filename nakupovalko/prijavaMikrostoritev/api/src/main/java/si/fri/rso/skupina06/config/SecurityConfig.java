package si.fri.rso.skupina06.config;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;

@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public").permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessHandler(oidcLogoutSuccessHandler())
            );
        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        return null;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint() {
        return "Access granted to user endpoint.";
    }

}
