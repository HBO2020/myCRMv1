import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICiviliteFournisseur, CiviliteFournisseur } from '../civilite-fournisseur.model';
import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class CiviliteFournisseurRoutingResolveService implements Resolve<ICiviliteFournisseur> {
  constructor(protected service: CiviliteFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICiviliteFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((civiliteFournisseur: HttpResponse<CiviliteFournisseur>) => {
          if (civiliteFournisseur.body) {
            return of(civiliteFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CiviliteFournisseur());
  }
}
