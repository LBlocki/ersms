package pl.ersms.core.domain;

import lombok.NonNull;

import java.util.Collection;

public record ClientPrincipal(@NonNull String identityProvider,
                              @NonNull String userId,
                              @NonNull String userDetails,
                              @NonNull Collection<String> userRoles) {
}

