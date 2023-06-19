package pl.ersms.core.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import pl.ersms.core.domain.MenuItem;
import pl.ersms.core.domain.Restaurant;
import pl.ersms.core.infrastructure.mssql.MenuItemRepository;
import pl.ersms.core.infrastructure.mssql.RestaurantRepository;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class Bootstrap {


    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @PostConstruct
    public void init() {
        System.out.println("kurwaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        var restaurant1 = Restaurant.builder()
                .menuItems(new HashSet<>())
                .name("Bp Puławska")
                .userId("test_user")
                .addressCity("warszawa")
                .addressStreet("Puławska")
                .addressBuildingNumber("21")
                .isApproved(true)
                .build();

        var menuItem1 = MenuItem.builder()
                .restaurant(restaurant1)
                .name("Duża paczka niespodzianka")
                .description("paczka niespodzianka ze świeżymi produktami nie sprzedanymi w tym dniu")
                .price(BigDecimal.TEN)
                .build();

        restaurant1.getMenuItems().add(menuItem1);

        restaurantRepository.save(restaurant1);
        menuItemRepository.save(menuItem1);

    }
}
