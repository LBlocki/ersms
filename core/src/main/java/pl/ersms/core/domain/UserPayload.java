package pl.ersms.core.domain;

import lombok.NonNull;

import java.util.Collection;

public record UserPayload(@NonNull String identityProvider,
                          @NonNull String userId,
                          @NonNull String userDetails,
                          @NonNull String accessToken,
                          @NonNull Collection<UserClaim> claims) {
}
