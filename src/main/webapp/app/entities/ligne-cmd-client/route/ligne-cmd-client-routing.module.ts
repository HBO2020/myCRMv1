import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LigneCmdClientComponent } from '../list/ligne-cmd-client.component';
import { LigneCmdClientDetailComponent } from '../detail/ligne-cmd-client-detail.component';
import { LigneCmdClientUpdateComponent } from '../update/ligne-cmd-client-update.component';
import { LigneCmdClientRoutingResolveService } from './ligne-cmd-client-routing-resolve.service';

const ligneCmdClientRoute: Routes = [
  {
    path: '',
    component: LigneCmdClientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LigneCmdClientDetailComponent,
    resolve: {
      ligneCmdClient: LigneCmdClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LigneCmdClientUpdateComponent,
    resolve: {
      ligneCmdClient: LigneCmdClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LigneCmdClientUpdateComponent,
    resolve: {
      ligneCmdClient: LigneCmdClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ligneCmdClientRoute)],
  exports: [RouterModule],
})
export class LigneCmdClientRoutingModule {}
