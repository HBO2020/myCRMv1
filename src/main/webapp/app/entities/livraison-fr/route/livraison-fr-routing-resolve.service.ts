import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILivraisonFr, LivraisonFr } from '../livraison-fr.model';
import { LivraisonFrService } from '../service/livraison-fr.service';

@Injectable({ providedIn: 'root' })
export class LivraisonFrRoutingResolveService implements Resolve<ILivraisonFr> {
  constructor(protected service: LivraisonFrService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraisonFr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((livraisonFr: HttpResponse<LivraisonFr>) => {
          if (livraisonFr.body) {
            return of(livraisonFr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LivraisonFr());
  }
}
