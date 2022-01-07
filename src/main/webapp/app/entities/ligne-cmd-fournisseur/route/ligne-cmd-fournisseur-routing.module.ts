import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LigneCmdFournisseurComponent } from '../list/ligne-cmd-fournisseur.component';
import { LigneCmdFournisseurDetailComponent } from '../detail/ligne-cmd-fournisseur-detail.component';
import { LigneCmdFournisseurUpdateComponent } from '../update/ligne-cmd-fournisseur-update.component';
import { LigneCmdFournisseurRoutingResolveService } from './ligne-cmd-fournisseur-routing-resolve.service';

const ligneCmdFournisseurRoute: Routes = [
  {
    path: '',
    component: LigneCmdFournisseurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LigneCmdFournisseurDetailComponent,
    resolve: {
      ligneCmdFournisseur: LigneCmdFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LigneCmdFournisseurUpdateComponent,
    resolve: {
      ligneCmdFournisseur: LigneCmdFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LigneCmdFournisseurUpdateComponent,
    resolve: {
      ligneCmdFournisseur: LigneCmdFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ligneCmdFournisseurRoute)],
  exports: [RouterModule],
})
export class LigneCmdFournisseurRoutingModule {}
