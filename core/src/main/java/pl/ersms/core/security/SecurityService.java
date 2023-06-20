package pl.ersms.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.ersms.core.utils.Roles;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isClient() {
        return getUserRoles().isEmpty();
    }

    public boolean isRestaurantOwner() {
        return getUserRoles().contains(Roles.RESTAURANT_OWNER);
    }

    public boolean isAdmin() {
        return getUserRoles().contains(Roles.ADMIN);
    }

    private Collection<String> getUserRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
