package pl.ersms.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.ersms.core.domain.UserPayload;
import pl.ersms.core.utils.Azure;
import pl.ersms.core.utils.Paths;

import java.io.IOException;
import java.util.HashSet;

@Slf4j
public class AzureClaimsMappingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain filterChain) throws IOException {

        var payload = objectMapper.readValue(request.getInputStream(), UserPayload.class);
        var claims = payload.claims();

        var issuer = claims.stream().filter(claim -> claim.typ().equals(Azure.ISSUER_CLAIM))
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Missing issuer claim for claims mapping."));

        if (!issuer.val().equals(Azure.ISSUER)) {
            throw new BadCredentialsException("Invalid issuer claim for claims mapping.");
        }

        var roles = new HashSet<String>();

        claims.stream()
                .filter(claim -> Azure.ROLES_CLAIM.equals(claim.typ()) || Azure.ROLE_CLAIM.equals(claim.typ()))
                .forEach(claim -> roles.add(claim.val()));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new UserRoles(roles)));

        log.info("User claims mapped. Providing details:\nUSER ID: {}. Roles: {}", payload.userId(), roles);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !Paths.AZURE_CLAIMS_MAPPING.equals(request.getRequestURI());
    }

    record UserRoles(HashSet<String> roles) {
    }
}
