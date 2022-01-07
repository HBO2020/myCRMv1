import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LigneLivClientComponent } from '../list/ligne-liv-client.component';
import { LigneLivClientDetailComponent } from '../detail/ligne-liv-client-detail.component';
import { LigneLivClientUpdateComponent } from '../update/ligne-liv-client-update.component';
import { LigneLivClientRoutingResolveService } from './ligne-liv-client-routing-resolve.service';

const ligneLivClientRoute: Routes = [
  {
    path: '',
    component: LigneLivClientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LigneLivClientDetailComponent,
    resolve: {
      ligneLivClient: LigneLivClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LigneLivClientUpdateComponent,
    resolve: {
      ligneLivClient: LigneLivClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LigneLivClientUpdateComponent,
    resolve: {
      ligneLivClient: LigneLivClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ligneLivClientRoute)],
  exports: [RouterModule],
})
export class LigneLivClientRoutingModule {}
