import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CartsComponent } from '../list/carts.component';
import { CartsDetailComponent } from '../detail/carts-detail.component';
import { CartsUpdateComponent } from '../update/carts-update.component';
import { CartsRoutingResolveService } from './carts-routing-resolve.service';

const cartsRoute: Routes = [
  {
    path: '',
    component: CartsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CartsDetailComponent,
    resolve: {
      carts: CartsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CartsUpdateComponent,
    resolve: {
      carts: CartsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CartsUpdateComponent,
    resolve: {
      carts: CartsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cartsRoute)],
  exports: [RouterModule],
})
export class CartsRoutingModule {}
