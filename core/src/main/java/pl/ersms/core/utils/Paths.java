package pl.ersms.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Paths {

    public static final String CREATE_NEW_MENU_ITEM = "/api/restaurant/menu-items/create";

    public static final String CREATE_NEW_RESTAURANT = "/api/restaurant/create";

    public static final String FETCH_RESTAURANT_MENU_ITEMS = "/api/restaurant/menu-items/fetch";

    public static final String DELETE_RESTAURANT = "/api/restaurant/delete";

    public static final String DELETE_MENU_ITEM = "/api/restaurant/menu-items/delete";

    public static final String ADMIN_APPROVE_RESTAURANT = "/api/admin/restaurant/approve";
    public static final String ADMIN_GET_ALL_RESTAURANTS = "/api/admin/restaurants";

    public static final String ADMIN_DELETE_RESTAURANT = "/api/admin/restaurant/delete";

    public static final String CHANGE_MENU_ITEM_STATE_BY_RESTAURANT = "/api/restaurant/menu-items/change-state";

    public static final String CHANGE_MENU_ITEM_STATE_BY_CLIENT = "/api/client/menu-items/change-state";

    public static final String FETCH_ALL_PENDING_MENU_ITEMS = "/api/client/menu-items/fetch";

    public static final String FETCH_CLIENT_MENU_ITEMS_BY_STATE = "/api/client/menu-items/fetch-by-state";

    public static final String FETCH_OWN_RESTAURANT = "/api/restaurant/fetch";

    public static final String AZURE_CLAIMS_MAPPING = "/api/azure/claims-mapping";

}
