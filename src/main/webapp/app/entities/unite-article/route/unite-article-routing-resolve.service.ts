import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUniteArticle, UniteArticle } from '../unite-article.model';
import { UniteArticleService } from '../service/unite-article.service';

@Injectable({ providedIn: 'root' })
export class UniteArticleRoutingResolveService implements Resolve<IUniteArticle> {
  constructor(protected service: UniteArticleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUniteArticle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((uniteArticle: HttpResponse<UniteArticle>) => {
          if (uniteArticle.body) {
            return of(uniteArticle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UniteArticle());
  }
}
