import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneLivClient, LigneLivClient } from '../ligne-liv-client.model';
import { LigneLivClientService } from '../service/ligne-liv-client.service';

@Injectable({ providedIn: 'root' })
export class LigneLivClientRoutingResolveService implements Resolve<ILigneLivClient> {
  constructor(protected service: LigneLivClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneLivClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ligneLivClient: HttpResponse<LigneLivClient>) => {
          if (ligneLivClient.body) {
            return of(ligneLivClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneLivClient());
  }
}
