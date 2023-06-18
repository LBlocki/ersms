package pl.ersms.core.web.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.ersms.core.domain.MenuItem;
import pl.ersms.core.web.dto.MenuItemDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuItemConverter {

    public static MenuItemDTO convertMenuItemToDTO(@NonNull final MenuItem menuItem,
                                                   @NonNull final MenuItem.State state) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getRestaurant().getRestaurantId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                MenuItemDTO.StateDTO.valueOf(state.name())
        );
    }
}
