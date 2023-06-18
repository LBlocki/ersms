import {Component, Input} from '@angular/core';
import {SpinnerType} from "./spinner.service";

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent {

  @Input()
  message: string | undefined;

  @Input()
  spinnerType: SpinnerType | undefined;

  constructor() { }

  get loadingMenuItemsSpinner(): boolean {
    return SpinnerType.LOADING_MENU_ITEMS_SPINNER === this.spinnerType;
  }
}
