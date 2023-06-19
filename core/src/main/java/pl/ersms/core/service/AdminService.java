package pl.ersms.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ersms.core.domain.Restaurant;
import pl.ersms.core.infrastructure.mssql.RestaurantRepository;
import pl.ersms.core.security.SecurityService;
import pl.ersms.core.service.validator.AdminValidator;
import pl.ersms.core.web.converter.RestaurantConverter;
import pl.ersms.core.web.dto.RestaurantDTO;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final RestaurantRepository restaurantRepository;
    private final SecurityService securityService;
    private final AdminValidator adminValidator;

    @Transactional
    public void approveRestaurant(final Long restaurantId) {
        adminValidator.validateApproveRestaurantRequest(restaurantId);
        log.info("Approving restaurant with id {}, by administrator {}", restaurantId, securityService.getUsername());
        var restaurant = restaurantRepository.findByRestaurantId(restaurantId).orElseThrow();
        restaurant.setIsApproved(true);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public Collection<RestaurantDTO> fetchAllRestaurantsNotApproved() {
        adminValidator.validateFetchAllRestaurantsRequest();
        log.info("Fetching all restaurants by administrator {}", securityService.getUsername());
        var restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .filter(restaurant -> !restaurant.getIsApproved())
                .map(RestaurantConverter::convertRestaurantToDTO)
                .toList();
    }

    @Transactional
    public void deleteRestaurant(final Long restaurantId) {
        adminValidator.validateDeleteRestaurantRequest(restaurantId);
        log.info("Deleting restaurant with id {}, by administrator {}", restaurantId, securityService.getUsername());
        restaurantRepository.deleteByRestaurantId(restaurantId);
    }
}
