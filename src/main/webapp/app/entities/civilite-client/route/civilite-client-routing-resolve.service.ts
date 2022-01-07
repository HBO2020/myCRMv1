import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICiviliteClient, CiviliteClient } from '../civilite-client.model';
import { CiviliteClientService } from '../service/civilite-client.service';

@Injectable({ providedIn: 'root' })
export class CiviliteClientRoutingResolveService implements Resolve<ICiviliteClient> {
  constructor(protected service: CiviliteClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICiviliteClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((civiliteClient: HttpResponse<CiviliteClient>) => {
          if (civiliteClient.body) {
            return of(civiliteClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CiviliteClient());
  }
}
