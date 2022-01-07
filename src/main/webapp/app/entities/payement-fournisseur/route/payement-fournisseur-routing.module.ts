import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PayementFournisseurComponent } from '../list/payement-fournisseur.component';
import { PayementFournisseurDetailComponent } from '../detail/payement-fournisseur-detail.component';
import { PayementFournisseurUpdateComponent } from '../update/payement-fournisseur-update.component';
import { PayementFournisseurRoutingResolveService } from './payement-fournisseur-routing-resolve.service';

const payementFournisseurRoute: Routes = [
  {
    path: '',
    component: PayementFournisseurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayementFournisseurDetailComponent,
    resolve: {
      payementFournisseur: PayementFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayementFournisseurUpdateComponent,
    resolve: {
      payementFournisseur: PayementFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayementFournisseurUpdateComponent,
    resolve: {
      payementFournisseur: PayementFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(payementFournisseurRoute)],
  exports: [RouterModule],
})
export class PayementFournisseurRoutingModule {}
