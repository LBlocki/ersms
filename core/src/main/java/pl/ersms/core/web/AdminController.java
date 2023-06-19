package pl.ersms.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ersms.core.service.AdminService;
import pl.ersms.core.utils.Paths;
import pl.ersms.core.web.dto.RestaurantDTO;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = Paths.ADMIN_APPROVE_RESTAURANT + "/{restaurantId}")
    public void approveRestaurant(@PathVariable final Long restaurantId) {
        adminService.approveRestaurant(restaurantId);
    }

    @GetMapping(path = Paths.ADMIN_GET_ALL_RESTAURANTS)
    public Collection<RestaurantDTO> fetchAllRestaurantsNotApproved() {
        return adminService.fetchAllRestaurantsNotApproved();
    }

    @DeleteMapping(path = Paths.ADMIN_DELETE_RESTAURANT + "/{restaurantId}")
    public void deleteRestaurant(@PathVariable final Long restaurantId) {
        adminService.deleteRestaurant(restaurantId);
    }
}
