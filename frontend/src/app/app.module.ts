import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { MenuItemComponent } from './commons/menu-item/menu-item.component';
import { SpinnerComponent } from './commons/spinner/spinner.component';
import { OwnerComponent } from './owner/owner.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {NotificationHttpInterceptorService} from "./commons/notification/notification-http-interceptor.service";
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LocalheadersInterceptor} from "./localheaders.interceptor";
import { NewEntryModalComponent } from './owner/modal/new-entry-modal/new-entry-modal.component';
import {ModalModule} from "ngx-bootstrap/modal";
import {ReactiveFormsModule} from "@angular/forms";
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuItemComponent,
    SpinnerComponent,
    OwnerComponent,
    NewEntryModalComponent,
    AdminComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    ToastrModule.forRoot(),
    ModalModule.forRoot(),
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationHttpInterceptorService,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LocalheadersInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
