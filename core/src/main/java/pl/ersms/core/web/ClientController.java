package pl.ersms.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ersms.core.service.ClientService;
import pl.ersms.core.utils.Paths;
import pl.ersms.core.web.dto.MenuItemDTO;
import pl.ersms.core.web.request.ChangeMenuItemStateByClientRequest;
import pl.ersms.core.web.request.FetchClientMenuItemsRequest;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    @GetMapping(path = Paths.FETCH_ALL_PENDING_MENU_ITEMS, produces = "application/json")
    public Collection<MenuItemDTO> fetchAllPendingMenuItems() {
        return clientService.fetchAvailableMenuItemsFromRestaurants();
    }

    @PostMapping(path = Paths.FETCH_CLIENT_MENU_ITEMS_BY_STATE, produces = "application/json", consumes = "application/json")
    public Collection<MenuItemDTO> fetchClientMenuItemsByState(@RequestBody final FetchClientMenuItemsRequest request) {
        return clientService.fetchClientMenuItemsByState(request);
    }

    @PostMapping(path = Paths.CHANGE_MENU_ITEM_STATE_BY_CLIENT, produces = "application/json", consumes = "application/json")
    public MenuItemDTO changeMenuItemStateByClient(@RequestBody final ChangeMenuItemStateByClientRequest request) {
        return clientService.changeMenuItemStateByClient(request);
    }
}
