import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneCmdFournisseur, LigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class LigneCmdFournisseurRoutingResolveService implements Resolve<ILigneCmdFournisseur> {
  constructor(protected service: LigneCmdFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneCmdFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ligneCmdFournisseur: HttpResponse<LigneCmdFournisseur>) => {
          if (ligneCmdFournisseur.body) {
            return of(ligneCmdFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneCmdFournisseur());
  }
}
