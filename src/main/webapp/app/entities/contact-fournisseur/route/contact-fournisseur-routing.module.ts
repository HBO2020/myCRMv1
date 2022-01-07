import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContactFournisseurComponent } from '../list/contact-fournisseur.component';
import { ContactFournisseurDetailComponent } from '../detail/contact-fournisseur-detail.component';
import { ContactFournisseurUpdateComponent } from '../update/contact-fournisseur-update.component';
import { ContactFournisseurRoutingResolveService } from './contact-fournisseur-routing-resolve.service';

const contactFournisseurRoute: Routes = [
  {
    path: '',
    component: ContactFournisseurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactFournisseurDetailComponent,
    resolve: {
      contactFournisseur: ContactFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactFournisseurUpdateComponent,
    resolve: {
      contactFournisseur: ContactFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactFournisseurUpdateComponent,
    resolve: {
      contactFournisseur: ContactFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contactFournisseurRoute)],
  exports: [RouterModule],
})
export class ContactFournisseurRoutingModule {}
