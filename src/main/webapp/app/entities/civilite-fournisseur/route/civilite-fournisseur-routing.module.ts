import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CiviliteFournisseurComponent } from '../list/civilite-fournisseur.component';
import { CiviliteFournisseurDetailComponent } from '../detail/civilite-fournisseur-detail.component';
import { CiviliteFournisseurUpdateComponent } from '../update/civilite-fournisseur-update.component';
import { CiviliteFournisseurRoutingResolveService } from './civilite-fournisseur-routing-resolve.service';

const civiliteFournisseurRoute: Routes = [
  {
    path: '',
    component: CiviliteFournisseurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CiviliteFournisseurDetailComponent,
    resolve: {
      civiliteFournisseur: CiviliteFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CiviliteFournisseurUpdateComponent,
    resolve: {
      civiliteFournisseur: CiviliteFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CiviliteFournisseurUpdateComponent,
    resolve: {
      civiliteFournisseur: CiviliteFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(civiliteFournisseurRoute)],
  exports: [RouterModule],
})
export class CiviliteFournisseurRoutingModule {}
