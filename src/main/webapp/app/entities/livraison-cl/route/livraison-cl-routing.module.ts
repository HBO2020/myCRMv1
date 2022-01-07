import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LivraisonClComponent } from '../list/livraison-cl.component';
import { LivraisonClDetailComponent } from '../detail/livraison-cl-detail.component';
import { LivraisonClUpdateComponent } from '../update/livraison-cl-update.component';
import { LivraisonClRoutingResolveService } from './livraison-cl-routing-resolve.service';

const livraisonClRoute: Routes = [
  {
    path: '',
    component: LivraisonClComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LivraisonClDetailComponent,
    resolve: {
      livraisonCl: LivraisonClRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LivraisonClUpdateComponent,
    resolve: {
      livraisonCl: LivraisonClRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LivraisonClUpdateComponent,
    resolve: {
      livraisonCl: LivraisonClRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(livraisonClRoute)],
  exports: [RouterModule],
})
export class LivraisonClRoutingModule {}
