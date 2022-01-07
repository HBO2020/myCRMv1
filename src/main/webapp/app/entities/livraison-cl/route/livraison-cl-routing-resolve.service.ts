import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILivraisonCl, LivraisonCl } from '../livraison-cl.model';
import { LivraisonClService } from '../service/livraison-cl.service';

@Injectable({ providedIn: 'root' })
export class LivraisonClRoutingResolveService implements Resolve<ILivraisonCl> {
  constructor(protected service: LivraisonClService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraisonCl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((livraisonCl: HttpResponse<LivraisonCl>) => {
          if (livraisonCl.body) {
            return of(livraisonCl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LivraisonCl());
  }
}
