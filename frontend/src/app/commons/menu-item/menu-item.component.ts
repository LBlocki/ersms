import {Component, EventEmitter, Input, Output} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {faWindowClose} from '@fortawesome/free-solid-svg-icons';
import {MenuItemDto} from "../../../generated-code/models/menu-item-dto";
import {OwnerTypeConfig} from "../../owner/owner.component";

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
  menuItem: MenuItemDto | undefined;
  @Input()
  config: OwnerTypeConfig | undefined;
  @Output()
  deleteMenuItemEvent = new EventEmitter<number>();
  faWindowClose = faWindowClose;

  deleteMenuItem() {
    this.deleteMenuItemEvent.emit(this.menuItem?.id);
  }
}
