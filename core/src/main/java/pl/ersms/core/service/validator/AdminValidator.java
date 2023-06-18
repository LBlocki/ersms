package pl.ersms.core.service.validator;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ersms.core.infrastructure.mssql.RestaurantRepository;
import pl.ersms.core.security.SecurityService;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AdminValidator {

    private final SecurityService securityService;
    private final RestaurantRepository restaurantRepository;

    public void validateApproveRestaurantRequest(final Long restaurantId) {
        validateNotNullRequest(restaurantId);
        if (!securityService.isAdmin()) {
            throw new ConstraintViolationException("Only admins can approve restaurants", Collections.emptySet());
        }
        if (restaurantRepository.findByRestaurantId(restaurantId).isEmpty()) {
            throw new ConstraintViolationException("Restaurant with id " + restaurantId + " does not exist", Collections.emptySet());
        }
    }

    public void validateDeleteRestaurantRequest(final Long restaurantId) {
        validateNotNullRequest(restaurantId);
        if (!securityService.isAdmin()) {
            throw new ConstraintViolationException("Only admins can delete restaurants", Collections.emptySet());
        }
        if (restaurantRepository.findByRestaurantId(restaurantId).isEmpty()) {
            throw new ConstraintViolationException("Restaurant with id " + restaurantId + " does not exist", Collections.emptySet());
        }
    }


    private void validateNotNullRequest(final Object request) {
        if (Objects.isNull(request)) {
            throw new ConstraintViolationException("Request cannot be null", Collections.emptySet());
        }
    }
}
