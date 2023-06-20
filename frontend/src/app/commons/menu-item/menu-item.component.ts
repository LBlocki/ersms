import {Component, EventEmitter, Input, Output} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {faWindowClose} from '@fortawesome/free-solid-svg-icons';
import {MenuItemDto} from "../../../generated-code/models/menu-item-dto";
import {IconProp} from "@fortawesome/fontawesome-svg-core";

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({opacity: 0}),
            animate('.4s ease-out',
              style({opacity: 1}))
          ]
        )
      ]
    )
  ]
})
export class MenuItemComponent {

  @Input()
  menuItem: MenuItemDto  = {} as MenuItemDto;
  @Input()
  config: Config | undefined;
  @Output()
  deleteMenuItemEvent = new EventEmitter<number>();
  faWindowClose = faWindowClose;

  deleteMenuItem() {
    this.deleteMenuItemEvent.emit(this.menuItem?.id);
  }
}

export interface Config {
  topName: string;
  state: string;
  menuItems: MenuItemDto[];
  canBeRemoved: boolean;
  buttonConfigs: ButtonConfig[];
  spinnerId: number;
  shouldDisplayRestaurantName?: boolean;
}

export interface ButtonConfig {
  text: string;
  cssClass: string;
  icon: IconProp;
  action: (menuItem: MenuItemDto) => void;
}
