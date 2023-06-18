package pl.ersms.core.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocalAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
                                    final @NonNull HttpServletResponse response,
                                    final @NonNull FilterChain filterChain) throws ServletException, IOException {

        var authorities = retrieveAuthorities(request);
        var username = StringUtils.isNotBlank(request.getHeader("Local-username")) ?
                request.getHeader("Local-username") : "empty_user";
        SecurityContextHolder.getContext()
                .setAuthentication(new PreAuthenticatedAuthenticationToken(username, null, authorities));
        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> retrieveAuthorities(final @NonNull HttpServletRequest request) {
        final String roles = request.getHeader("Local-roles");
        if (StringUtils.isNotBlank(roles)) {
            String[] authorities = roles.split(",");
            return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
