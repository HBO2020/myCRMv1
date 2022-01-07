import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPayementClient, PayementClient } from '../payement-client.model';
import { PayementClientService } from '../service/payement-client.service';

@Injectable({ providedIn: 'root' })
export class PayementClientRoutingResolveService implements Resolve<IPayementClient> {
  constructor(protected service: PayementClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayementClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((payementClient: HttpResponse<PayementClient>) => {
          if (payementClient.body) {
            return of(payementClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayementClient());
  }
}
