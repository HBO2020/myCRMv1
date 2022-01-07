import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LivraisonFrComponent } from '../list/livraison-fr.component';
import { LivraisonFrDetailComponent } from '../detail/livraison-fr-detail.component';
import { LivraisonFrUpdateComponent } from '../update/livraison-fr-update.component';
import { LivraisonFrRoutingResolveService } from './livraison-fr-routing-resolve.service';

const livraisonFrRoute: Routes = [
  {
    path: '',
    component: LivraisonFrComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LivraisonFrDetailComponent,
    resolve: {
      livraisonFr: LivraisonFrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LivraisonFrUpdateComponent,
    resolve: {
      livraisonFr: LivraisonFrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LivraisonFrUpdateComponent,
    resolve: {
      livraisonFr: LivraisonFrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(livraisonFrRoute)],
  exports: [RouterModule],
})
export class LivraisonFrRoutingModule {}
