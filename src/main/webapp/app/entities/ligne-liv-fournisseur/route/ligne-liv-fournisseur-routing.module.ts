import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LigneLivFournisseurComponent } from '../list/ligne-liv-fournisseur.component';
import { LigneLivFournisseurDetailComponent } from '../detail/ligne-liv-fournisseur-detail.component';
import { LigneLivFournisseurUpdateComponent } from '../update/ligne-liv-fournisseur-update.component';
import { LigneLivFournisseurRoutingResolveService } from './ligne-liv-fournisseur-routing-resolve.service';

const ligneLivFournisseurRoute: Routes = [
  {
    path: '',
    component: LigneLivFournisseurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LigneLivFournisseurDetailComponent,
    resolve: {
      ligneLivFournisseur: LigneLivFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LigneLivFournisseurUpdateComponent,
    resolve: {
      ligneLivFournisseur: LigneLivFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LigneLivFournisseurUpdateComponent,
    resolve: {
      ligneLivFournisseur: LigneLivFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ligneLivFournisseurRoute)],
  exports: [RouterModule],
})
export class LigneLivFournisseurRoutingModule {}
