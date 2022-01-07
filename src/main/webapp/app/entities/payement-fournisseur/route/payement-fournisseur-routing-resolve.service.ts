import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPayementFournisseur, PayementFournisseur } from '../payement-fournisseur.model';
import { PayementFournisseurService } from '../service/payement-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class PayementFournisseurRoutingResolveService implements Resolve<IPayementFournisseur> {
  constructor(protected service: PayementFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayementFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((payementFournisseur: HttpResponse<PayementFournisseur>) => {
          if (payementFournisseur.body) {
            return of(payementFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayementFournisseur());
  }
}
