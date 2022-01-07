import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneLivFournisseur, LigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class LigneLivFournisseurRoutingResolveService implements Resolve<ILigneLivFournisseur> {
  constructor(protected service: LigneLivFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneLivFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ligneLivFournisseur: HttpResponse<LigneLivFournisseur>) => {
          if (ligneLivFournisseur.body) {
            return of(ligneLivFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneLivFournisseur());
  }
}
