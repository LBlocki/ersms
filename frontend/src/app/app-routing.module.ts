import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OwnerComponent} from "./owner/owner.component";
import {AdminComponent} from "./admin/admin.component";
import {UserComponent} from "./user/user.component";


const routes: Routes = [
  {
    path: 'user',
    component: UserComponent,
  },
  {
    path: 'admin',
    component: AdminComponent,
  },
  {
    path: 'owner',
    component: OwnerComponent,
  },
  {
    path: '**',
    component: UserComponent,
    pathMatch: 'full',
  },
  {path: '', redirectTo: '/user', pathMatch: 'full'}, // redirect to
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
