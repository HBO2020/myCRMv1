import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContactFournisseur, ContactFournisseur } from '../contact-fournisseur.model';
import { ContactFournisseurService } from '../service/contact-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class ContactFournisseurRoutingResolveService implements Resolve<IContactFournisseur> {
  constructor(protected service: ContactFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contactFournisseur: HttpResponse<ContactFournisseur>) => {
          if (contactFournisseur.body) {
            return of(contactFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactFournisseur());
  }
}
