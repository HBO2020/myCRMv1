import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContactClient, ContactClient } from '../contact-client.model';
import { ContactClientService } from '../service/contact-client.service';

@Injectable({ providedIn: 'root' })
export class ContactClientRoutingResolveService implements Resolve<IContactClient> {
  constructor(protected service: ContactClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contactClient: HttpResponse<ContactClient>) => {
          if (contactClient.body) {
            return of(contactClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactClient());
  }
}
