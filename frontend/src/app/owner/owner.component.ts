import {Component, OnDestroy, OnInit} from '@angular/core';
import {faArrowRight, faPlus, faTimes} from '@fortawesome/free-solid-svg-icons';
import {SpinnerService, SpinnerType} from "../commons/spinner/spinner.service";
import {RestaurantControllerService} from "../../generated-code/services/restaurant-controller.service";
import {MenuItemDto} from "../../generated-code/models/menu-item-dto";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NewEntryModalComponent} from "./modal/new-entry-modal/new-entry-modal.component";
import {Config} from "../commons/menu-item/menu-item.component";
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit, OnDestroy {

  configs: Config[] = [];

  faPlus = faPlus;

  bsModalRef!: BsModalRef;

  constructor(private spinnerService: SpinnerService, private restaurantControllerService: RestaurantControllerService,
              private modalService: BsModalService) {
    this.configs.push(
      {
        topName: "Awaiting reservation",
        state: "AVAILABLE",
        menuItems: [],
        canBeRemoved: true,
        buttonConfigs: [],
        spinnerId: 1
      },
      {
        topName: "Requires confirmation",
        state: "PENDING",
        menuItems: [],
        canBeRemoved: true,
        buttonConfigs: [
          {
            text: "Zaakceptuj",
            cssClass: "btn btn-success",
            icon: faArrowRight,
            action: (menuItem: MenuItemDto) => {
             restaurantControllerService.changeMenuItemStateByRestaurant({
               body: {
                 change: 'APPROVE',
                  menuItemId: menuItem.id as number
               }
             }).subscribe((menuItem: MenuItemDto) => {
               this.resortMenuItem(menuItem);
             });
            }
          },
          {
            text: "Odrzuć",
            cssClass: "btn btn-danger",
            icon: faTimes,
            action: (menuItem: MenuItemDto) => {
              restaurantControllerService.changeMenuItemStateByRestaurant({
                body: {
                  change: 'DENY',
                  menuItemId: menuItem.id as number
                }
              }).subscribe((menuItem: MenuItemDto) => {
                this.resortMenuItem(menuItem);
              });
            }
          }
        ],
        spinnerId: 2
      },
      {
        topName: "Awaiting pickup",
        state: "RESERVED",
        menuItems: [],
        canBeRemoved: false,
        buttonConfigs: [
          {
            text: "Zakończ",
            cssClass: "btn btn-success",
            icon: faArrowRight,
            action: (menuItem: MenuItemDto) => {
              restaurantControllerService.changeMenuItemStateByRestaurant({
                body: {
                  change: 'COMPLETE',
                  menuItemId: menuItem.id as number
                }
              }).subscribe((menuItem: MenuItemDto) => {
                this.resortMenuItem(menuItem);
              });
            }
          }
        ],
        spinnerId: 3
      },
      {
        topName: "Finished",
        state: "FINISHED",
        menuItems: [],
        canBeRemoved: false,
        buttonConfigs: [],
        spinnerId: 4
      }
    );
  }

  private subscription: Subscription = {} as Subscription;

  ngOnInit(): void {
    this.loadItems(true);
    const intervalTime = 10000;
    this.subscription = interval(intervalTime).subscribe(() => {
      this.loadItems();
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }


  loadItems(withSpinners: boolean = false) {
    this.configs?.forEach(config => {
      if (withSpinners) {
        this.spinnerService.enableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
      }
      this.restaurantControllerService.fetchRestaurantMenuItems({
        body: {
          state: config.state as any
        }
      }).subscribe({
        next: (data: Array<MenuItemDto>) => {
          config.menuItems = data;
          this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
        },
        error: () => {
          this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
          config.menuItems = [];
        }
      });
    });
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
      this.configs.forEach(config => {
        config.menuItems = config.menuItems.filter(item => item.id != menuItemId);
      })
    });
  }

  resortMenuItem(menuItem: MenuItemDto) {
    this.configs.forEach(config => {
      config.menuItems = config.menuItems.filter(item => item.id != menuItem.id);
    });
    this.configs.find(config => config.state == menuItem.state)?.menuItems.push(menuItem);
  }

  displayNewEntryModal() {
    this.bsModalRef = this.modalService.show(NewEntryModalComponent)
    this.bsModalRef.content.savedNewEntryStatusesChanged.subscribe(() => {
      this.loadItems();
    });
  }
}
