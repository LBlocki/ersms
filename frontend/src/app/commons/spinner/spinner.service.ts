import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  private enabledSpinnersMap: Map<SpinnerType, number[]> = new Map<SpinnerType, number[]>();

  constructor() {
    this.enabledSpinnersMap.set(SpinnerType.LOADING_MENU_ITEMS_SPINNER, []);
  }

  public enableSpinner(spinnerType: SpinnerType, spinnerId: number): void {
    const spinnerTypeIds = this.enabledSpinnersMap.get(spinnerType);
    if (spinnerTypeIds && !spinnerTypeIds.find(val => val === spinnerId)) {
      this.enabledSpinnersMap.get(spinnerType)?.push(spinnerId);
    }
  }

  public disableSpinner(spinnerType: SpinnerType, spinnerId: number): void {
    const spinnerTypeIds = this.enabledSpinnersMap.get(spinnerType);
    if (spinnerTypeIds && spinnerTypeIds.find(val => val === spinnerId)) {
      this.enabledSpinnersMap.delete(spinnerType);
      this.enabledSpinnersMap.set(spinnerType, spinnerTypeIds.filter(val => val !== spinnerId));
    }
  }

  public isSpinnerEnabled(spinnerType: SpinnerType, spinnerId: number): boolean {
    const spinnerTypeIds = this.enabledSpinnersMap.get(spinnerType);
      return spinnerTypeIds ? spinnerTypeIds.filter(val => val === spinnerId).length > 0 : false;
  }
}

export enum SpinnerType {
  LOADING_MENU_ITEMS_SPINNER = 'LOADING_MENU_ITEMS_SPINNER'
}
