package pl.ersms.core.infrastructure.mssql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pl.ersms.core.domain.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByName(@NonNull String name);

    Optional<Restaurant> findByUserId(@NonNull String userId);

    Optional<Restaurant> findByRestaurantId(@NonNull Long restaurantId);

    void deleteByRestaurantId(@NonNull Long restaurantId);

}