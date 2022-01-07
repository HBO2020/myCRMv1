import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PayementClientComponent } from '../list/payement-client.component';
import { PayementClientDetailComponent } from '../detail/payement-client-detail.component';
import { PayementClientUpdateComponent } from '../update/payement-client-update.component';
import { PayementClientRoutingResolveService } from './payement-client-routing-resolve.service';

const payementClientRoute: Routes = [
  {
    path: '',
    component: PayementClientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayementClientDetailComponent,
    resolve: {
      payementClient: PayementClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayementClientUpdateComponent,
    resolve: {
      payementClient: PayementClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayementClientUpdateComponent,
    resolve: {
      payementClient: PayementClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(payementClientRoute)],
  exports: [RouterModule],
})
export class PayementClientRoutingModule {}
