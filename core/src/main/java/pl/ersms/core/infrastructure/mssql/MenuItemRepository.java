package pl.ersms.core.infrastructure.mssql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pl.ersms.core.domain.MenuItem;

import java.util.Collection;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Collection<MenuItem> findByRestaurant_RestaurantIdAndPendingUserIdNull(@NonNull Long restaurantId);

    Collection<MenuItem> findByRestaurant_RestaurantIdAndPendingUserIdNotNullAndAcceptedUserIdNull(@NonNull Long restaurantId);

    Collection<MenuItem> findByRestaurant_RestaurantIdAndAcceptedUserIdNotNullAndCollectedDateNull(@NonNull Long restaurantId);

    Collection<MenuItem> findByRestaurant_RestaurantIdAndAcceptedUserIdNotNullAndCollectedDateNotNull(@NonNull Long restaurantId);

    void deleteById(@NonNull Long menuItemId);

    Collection<MenuItem> findAllByPendingUserIdNull();

    Collection<MenuItem> findAllByPendingUserIdAndAcceptedUserIdIsNull(@NonNull String userId);

    Collection<MenuItem> findAllByAcceptedUserIdAndCollectedDateIsNull(@NonNull String userId);

    Collection<MenuItem> findAllByAcceptedUserIdAndCollectedDateIsNotNull(@NonNull String userId);

}