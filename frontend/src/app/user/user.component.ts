import {Component, OnDestroy, OnInit} from '@angular/core';
import {Config} from "../commons/menu-item/menu-item.component";
import {SpinnerService, SpinnerType} from "../commons/spinner/spinner.service";
import {faArrowRight, faTimes} from "@fortawesome/free-solid-svg-icons";
import {MenuItemDto} from "../../generated-code/models/menu-item-dto";
import {ClientControllerService} from "../../generated-code/services/client-controller.service";
import {interval, Subscription, take} from "rxjs";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {

  configs: Config[] = [];

  constructor(private spinnerService: SpinnerService, private clientControllerService: ClientControllerService) {
    this.configs.push(
      {
        topName: "Available for reservation",
        state: "AVAILABLE",
        menuItems: [],
        canBeRemoved: true,
        buttonConfigs: [
          {
            text: "Reserve",
            cssClass: "btn btn-success",
            icon: faArrowRight,
            action: (menuItem: MenuItemDto) => {
              clientControllerService.changeMenuItemStateByClient({
                body: {
                  change: 'RESERVE',
                  menuItemId: menuItem.id as number
                }
              }).subscribe((menuItem: MenuItemDto) => {
                this.resortMenuItem(menuItem);
              });
            }
          }],
        spinnerId: 1
      },
      {
        topName: "Awaiting confirmation from restaurant",
        state: "PENDING",
        menuItems: [],
        canBeRemoved: true,
        buttonConfigs: [
          {
            text: "Cancel",
            cssClass: "btn btn-danger",
            icon: faTimes,
            action: (menuItem: MenuItemDto) => {
              clientControllerService.changeMenuItemStateByClient({
                body: {
                  change: 'CANCEL',
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
            text: "Cancel",
            cssClass: "btn btn-danger",
            icon: faTimes,
            action: (menuItem: MenuItemDto) => {
              clientControllerService.changeMenuItemStateByClient({
                body: {
                  change: 'CANCEL',
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

  loadItems(withSpinners: boolean = true) {
    this.configs?.forEach(config => {

      if (config.state !== "AVAILABLE") {
        if (withSpinners) {
          this.spinnerService.enableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
        }

        this.clientControllerService.fetchClientMenuItemsByState({
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
      } else {
        if (withSpinners) {
          this.spinnerService.enableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
        }
        this.clientControllerService.fetchAllPendingMenuItems().subscribe({
          next: (data: Array<MenuItemDto>) => {
            config.menuItems = data;
            this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
          },
          error: () => {
            this.spinnerService.disableSpinner(SpinnerType.LOADING_MENU_ITEMS_SPINNER, config.spinnerId);
            config.menuItems = [];
          }
        });
      }
    });
  }

  resortMenuItem(menuItem: MenuItemDto) {
    this.configs.forEach(config => {
      config.menuItems = config.menuItems.filter(item => item.id != menuItem.id);
    });
    this.configs.find(config => config.state == menuItem.state)?.menuItems.push(menuItem);
  }
  isSpinnerEnabled(spinnerId: number): boolean {
    return this.spinnerService.isSpinnerEnabled(SpinnerType.LOADING_MENU_ITEMS_SPINNER, spinnerId);
  }

  getSpinnerType(): SpinnerType {
    return SpinnerType.LOADING_MENU_ITEMS_SPINNER;
  }
}
