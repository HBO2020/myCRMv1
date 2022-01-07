import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarts, Carts } from '../carts.model';
import { CartsService } from '../service/carts.service';

@Injectable({ providedIn: 'root' })
export class CartsRoutingResolveService implements Resolve<ICarts> {
  constructor(protected service: CartsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((carts: HttpResponse<Carts>) => {
          if (carts.body) {
            return of(carts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Carts());
  }
}
