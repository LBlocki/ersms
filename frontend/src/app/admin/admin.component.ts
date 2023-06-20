import {Component, OnInit} from '@angular/core';
import {SpinnerService, SpinnerType} from "../commons/spinner/spinner.service";
import {faArrowRight, faTimes} from "@fortawesome/free-solid-svg-icons";
import {RestaurantDto} from "../../generated-code/models/restaurant-dto";
import {AdminControllerService} from "../../generated-code/services/admin-controller.service";
import {IconProp} from "@fortawesome/fontawesome-svg-core";
import {MenuItemDto} from "../../generated-code/models/menu-item-dto";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  config: AdminTypeConfig = {
    restaurants: [],
    buttonConfigs: [
      {
        text: "Zaakceptuj",
        cssClass: "btn btn-success",
        icon: faArrowRight,
        action: (restaurant: RestaurantDto) => {
          console.log('siema');
          this.adminControllerService.approveRestaurant({
            restaurantId: restaurant.id as number
          }).subscribe(() => {
            this.config.restaurants = this.config.restaurants.filter(r => r.id !== restaurant.id);
          });
        }
      },
      {
        text: "Odrzuć",
        cssClass: "btn btn-danger",
        icon: faTimes,
        action: (restaurant: RestaurantDto) => {
          this.adminControllerService.deleteRestaurant1({
            restaurantId: restaurant.id as number
          }).subscribe(() => {
            this.config.restaurants = this.config.restaurants.filter(r => r.id !== restaurant.id);
          });
        }
      }
    ],
    spinnerId: 10
  };

  constructor(private spinnerService: SpinnerService, private adminControllerService: AdminControllerService) {
  }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems() {
    this.spinnerService.enableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, this.config?.spinnerId);
    this.adminControllerService.fetchAllAwaitingForApprovalRestaurants().subscribe({
      next: (data: Array<RestaurantDto>) => {
        this.config.restaurants = data;
        this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, this.config.spinnerId);
      },
      error: () => {
        this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, this.config.spinnerId);
        this.config.restaurants = [];
      }
    });
  }

  isSpinnerEnabled(spinnerId: number): boolean {
    return this.spinnerService.isSpinnerEnabled(SpinnerType.LOADING_MENU_ITEMS_SPINNER, spinnerId);
  }

  getSpinnerType(): SpinnerType {
    return SpinnerType.LOADING_MENU_ITEMS_SPINNER;
  }
}

export interface AdminTypeConfig {
  restaurants: RestaurantDto[];
  buttonConfigs: AdminButtonConfig[];
  spinnerId: number;
}

export interface AdminButtonConfig {
  text: string;
  cssClass: string;
  icon: IconProp;
  action: (restaurant: RestaurantDto) => void;
}
