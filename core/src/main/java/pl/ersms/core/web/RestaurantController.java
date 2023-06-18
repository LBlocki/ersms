package pl.ersms.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ersms.core.service.RestaurantService;
import pl.ersms.core.utils.Paths;
import pl.ersms.core.web.dto.MenuItemDTO;
import pl.ersms.core.web.dto.RestaurantDTO;
import pl.ersms.core.web.request.ChangeMenuItemStateByRestaurantRequest;
import pl.ersms.core.web.request.CreateNewMenuItemRequest;
import pl.ersms.core.web.request.CreateNewRestaurantRequest;
import pl.ersms.core.web.request.FetchRestaurantMenuItemsRequest;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(path = Paths.FETCH_RESTAURANT_MENU_ITEMS, produces = "application/json")
    public Collection<MenuItemDTO> fetchRestaurantMenuItems(@RequestBody final FetchRestaurantMenuItemsRequest request) {
        return restaurantService.retrieveMenuItemsFromRestaurantWithState(request);
    }

    @PostMapping(path = Paths.CREATE_NEW_RESTAURANT, consumes = "application/json", produces = "application/json")
    public RestaurantDTO createNewRestaurant(@RequestBody final CreateNewRestaurantRequest request) {
        return restaurantService.createNewRestaurant(request);
    }

    @GetMapping(path = Paths.FETCH_OWN_RESTAURANT, produces = "application/json")
    public RestaurantDTO fetchOwnRestaurant() {
        return restaurantService.fetchOwnRestaurant();
    }

    @PostMapping(path = Paths.CREATE_NEW_MENU_ITEM, consumes = "application/json", produces = "application/json")
    public MenuItemDTO createNewMenuItem(@RequestBody final CreateNewMenuItemRequest request) {
        return restaurantService.createNewMenuItem(request);
    }

    @PostMapping(path = Paths.CHANGE_MENU_ITEM_STATE_BY_RESTAURANT, consumes = "application/json", produces = "application/json")
    public MenuItemDTO changeMenuItemStateByRestaurant(@RequestBody final ChangeMenuItemStateByRestaurantRequest request) {
        return restaurantService.changeMenuItemStateByRestaurant(request);
    }

    @DeleteMapping(path = Paths.DELETE_RESTAURANT)
    public void deleteRestaurant() {
        restaurantService.deleteRestaurant();
    }

    @DeleteMapping(path = Paths.DELETE_MENU_ITEM + "/{menuItemId}")
    public void deleteMenuItem(@PathVariable final Long menuItemId) {
        restaurantService.deleteMenuItem(menuItemId);
    }
}
