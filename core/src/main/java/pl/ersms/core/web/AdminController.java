package pl.ersms.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ersms.core.service.AdminService;
import pl.ersms.core.utils.Paths;


@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = Paths.ADMIN_APPROVE_RESTAURANT + "/{restaurantId}")
    public void approveRestaurant(@PathVariable final Long restaurantId) {
        adminService.approveRestaurant(restaurantId);
    }

    @DeleteMapping(path = Paths.ADMIN_DELETE_RESTAURANT + "/{restaurantId}")
    public void deleteRestaurant(@PathVariable final Long restaurantId) {
        adminService.deleteRestaurant(restaurantId);
    }
}
