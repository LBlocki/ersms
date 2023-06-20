package pl.ersms.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.ersms.core.domain.ClientPrincipal;
import pl.ersms.core.utils.Azure;
import pl.ersms.core.utils.Paths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

@Slf4j
public class AzureJwtTokenFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        var header = request.getHeader(Azure.CLIENT_PRINCIPAL_HEADER);

        if (StringUtils.isBlank(header)) {
            log.info("No Azure AD token found in request. Skipping authentication attempt.");
            filterChain.doFilter(request, response);
            return;
        }

        var decodedJson = new String(Base64.getUrlDecoder().decode(header), StandardCharsets.UTF_8);
        var clientPrincipal = objectMapper.readValue(decodedJson, ClientPrincipal.class);
        var roles = clientPrincipal.userRoles();
        var userId = clientPrincipal.userId();

        if (roles.isEmpty()) {
            throw new BadCredentialsException("Missing user roles. User must have at least one role defined");
        }

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        var authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Paths.AZURE_CLAIMS_MAPPING.equals(request.getRequestURI());
    }
}