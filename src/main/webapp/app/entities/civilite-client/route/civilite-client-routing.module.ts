import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CiviliteClientComponent } from '../list/civilite-client.component';
import { CiviliteClientDetailComponent } from '../detail/civilite-client-detail.component';
import { CiviliteClientUpdateComponent } from '../update/civilite-client-update.component';
import { CiviliteClientRoutingResolveService } from './civilite-client-routing-resolve.service';

const civiliteClientRoute: Routes = [
  {
    path: '',
    component: CiviliteClientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CiviliteClientDetailComponent,
    resolve: {
      civiliteClient: CiviliteClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CiviliteClientUpdateComponent,
    resolve: {
      civiliteClient: CiviliteClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CiviliteClientUpdateComponent,
    resolve: {
      civiliteClient: CiviliteClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(civiliteClientRoute)],
  exports: [RouterModule],
})
export class CiviliteClientRoutingModule {}
