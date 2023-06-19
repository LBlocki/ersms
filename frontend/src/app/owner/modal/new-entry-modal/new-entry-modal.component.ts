import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";
import {faWindowClose} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RestaurantControllerService} from "../../../../generated-code/services/restaurant-controller.service";


@Component({
  selector: 'app-new-entry-modal',
  templateUrl: './new-entry-modal.component.html',
  styleUrls: ['./new-entry-modal.component.scss']
})
export class NewEntryModalComponent implements OnInit{

  faWindowClose = faWindowClose;

  @Output() savedNewEntryStatusesChanged = new EventEmitter<boolean>();
  newItemForm: FormGroup;

  constructor(private bsModalRef: BsModalRef, private fromBuilder: FormBuilder, private restaurantControllerService: RestaurantControllerService ) {
    this.newItemForm = this.fromBuilder.group({
      name: ['', Validators.required],
      description: [''],
      price: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]]
    })
  }

  ngOnInit(): void {

  }

  closeModal() {
    this.savedNewEntryStatusesChanged.next(true)
    this.bsModalRef.hide();
  }

  saveModal() {
    console.log("save")
    this.restaurantControllerService.createNewMenuItem({body: {
      name: this.newItemForm.get('name')?.value,
      description: this.newItemForm.get('description')?.value,
      price: this.newItemForm.get('price')?.value
      }}).subscribe(() => this.closeModal())
  }
}
