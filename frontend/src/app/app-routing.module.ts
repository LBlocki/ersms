import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OwnerComponent} from "./owner/owner.component";


const routes: Routes = [
  {
    path: 'user',
    component: OwnerComponent,
  },
  {
    path: 'admin',
    component: OwnerComponent,
  },
  {
    path: 'owner',
    component: OwnerComponent,
  },
  {
    path: '**',
    component: OwnerComponent,
    pathMatch: 'full',
  },
  {path: '', redirectTo: '/owner', pathMatch: 'full'}, // redirect to
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
