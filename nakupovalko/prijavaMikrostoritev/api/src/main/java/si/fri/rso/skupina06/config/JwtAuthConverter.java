package si.fri.rso.skupina06.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream())
                        .collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities, getName(jwt));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;

        //this is in jwt!
        /*
        "resource_access": {
            "realm-management": {
              "roles": [
                "create-client"
              ]
            },
            "nakupovalko-prijava-client": {
              "roles": [
                "admin"
              ]
            },*/
        if(jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");
        if(resourceAccess.get("nakupovalko-prijava-client") == null) {
            return Set.of(); //no roles
        }

        resource = (Map<String, Object>) resourceAccess.get("nakupovalko-prijava-client");

        // role extraction
        return ((Collection<String>) resource.get("roles"))
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) //needs to have this prefix!
                .collect(Collectors.toSet());
    }

    //we want to extract "preferred_username": "vm0012"
    private String getName(Jwt jwt) {
        return jwt.getClaim("preferred_username");
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super AbstractAuthenticationToken, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
