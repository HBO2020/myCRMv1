import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UniteArticleComponent } from '../list/unite-article.component';
import { UniteArticleDetailComponent } from '../detail/unite-article-detail.component';
import { UniteArticleUpdateComponent } from '../update/unite-article-update.component';
import { UniteArticleRoutingResolveService } from './unite-article-routing-resolve.service';

const uniteArticleRoute: Routes = [
  {
    path: '',
    component: UniteArticleComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UniteArticleDetailComponent,
    resolve: {
      uniteArticle: UniteArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UniteArticleUpdateComponent,
    resolve: {
      uniteArticle: UniteArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UniteArticleUpdateComponent,
    resolve: {
      uniteArticle: UniteArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(uniteArticleRoute)],
  exports: [RouterModule],
})
export class UniteArticleRoutingModule {}
