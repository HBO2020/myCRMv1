import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContactClientComponent } from '../list/contact-client.component';
import { ContactClientDetailComponent } from '../detail/contact-client-detail.component';
import { ContactClientUpdateComponent } from '../update/contact-client-update.component';
import { ContactClientRoutingResolveService } from './contact-client-routing-resolve.service';

const contactClientRoute: Routes = [
  {
    path: '',
    component: ContactClientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactClientDetailComponent,
    resolve: {
      contactClient: ContactClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactClientUpdateComponent,
    resolve: {
      contactClient: ContactClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactClientUpdateComponent,
    resolve: {
      contactClient: ContactClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contactClientRoute)],
  exports: [RouterModule],
})
export class ContactClientRoutingModule {}
