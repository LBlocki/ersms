import {Component, OnInit} from '@angular/core';
import {faPlus, faTimes, faArrowRight} from '@fortawesome/free-solid-svg-icons';
import {SpinnerService, SpinnerType} from "../commons/spinner/spinner.service";
import {RestaurantControllerService} from "../../generated-code/services/restaurant-controller.service";
import {MenuItemDto} from "../../generated-code/models/menu-item-dto";
import {IconProp} from "@fortawesome/fontawesome-svg-core";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {

  configs: OwnerTypeConfig[] = [];

  faPlus = faPlus;

  constructor(private spinnerService: SpinnerService, private restaurantControllerService: RestaurantControllerService) {
    this.configs.push(
      {
        topName: "Oczekujące",
        state: "AVAILABLE",
        menuItems: [{
          id: 1,
          name: "Pizza",
          description: "Pizza",
          price: 10,
          state: "AVAILABLE"
        },
          {
            id: 2,
            name: "Pasta",
            description: "Pasta",
            price: 10,
            state: "AVAILABLE"
          }],
        canBeRemoved: true,
        buttonConfigs: [],
        spinnerId: 1
      },
      {
        topName: "Do potwierdzenia",
        state: "PENDING",
        menuItems: [
          {
            id: 4,
            name: "Soup",
            description: "Soup",
            price: 10,
            "state": "PENDING"
          }
        ],
        canBeRemoved: true,
        buttonConfigs: [
          {
            text: "Zaakceptuj",
            cssClass: "btn btn-success",
            icon: faArrowRight,
            action: () => {
              console.log("Zaakceptuj");
              //todo
            }
          },
          {
            text: "Odrzuć",
            cssClass: "btn btn-danger",
            icon: faTimes,
            action: () => {
              console.log("Odrzuć");
              //todo
            }
          }
        ],
        spinnerId: 2
      },
      {
        topName: "Zarezerwowane",
        state: "RESERVED",
        menuItems: [{
          id: 3,
          name: "Salad",
          description: "Salad",
          price: 10,
          "state": "RESERVED"
        }],
        canBeRemoved: false,
        buttonConfigs: [
          {
            text: "Zakończ",
            cssClass: "btn btn-success",
            icon: faArrowRight,
            action: () => {
              console.log("Zakończ");
              //todo
            }
          }
        ],
        spinnerId: 3
      },
      {
        topName: "Zakończone",
        state: "FINISHED",
        menuItems: [],
        canBeRemoved: false,
        buttonConfigs: [],
        spinnerId: 4
      }
    );
  }

  ngOnInit(): void {
    // this.configs?.forEach(config => {
    //   this.spinnerService.enableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
    //   this.restaurantControllerService.fetchRestaurantMenuItems({
    //     body: {
    //       state: config.state
    //     }
    //   }).subscribe({
    //     next: (data: Array<MenuItemDto>) => {
    //       config.menuItems = data;
    //       this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
    //     },
    //     error: () => {
    //       this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
    //       config.menuItems = [];
    //     }
    //   });
    // });
  }

  isSpinnerEnabled(spinnerId: number): boolean {
    return this.spinnerService.isSpinnerEnabled(SpinnerType.LOADING_MENU_ITEMS_SPINNER, spinnerId);
  }

  getSpinnerType(): SpinnerType {
    return SpinnerType.LOADING_MENU_ITEMS_SPINNER;
  }

  deleteMenuItem(menuItemId: number) {
    this.restaurantControllerService.deleteMenuItem({
      menuItemId: menuItemId
    }).subscribe(_ => {
      //todo odfiltrować
    });
  }

  displayNewEntryModal() {
    //todo
    console.log('todo')
  }
}

export interface OwnerTypeConfig {
  topName: string;
  state: "AVAILABLE" | "PENDING" | "RESERVED" | "FINISHED";
  menuItems: MenuItemDto[];
  canBeRemoved: boolean;
  buttonConfigs: ButtonConfig[];
  spinnerId: number;
}

export interface ButtonConfig {
  text: string;
  cssClass: string;
  icon: IconProp;
  action: () => void;
}
