import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneCmdClient, LigneCmdClient } from '../ligne-cmd-client.model';
import { LigneCmdClientService } from '../service/ligne-cmd-client.service';

@Injectable({ providedIn: 'root' })
export class LigneCmdClientRoutingResolveService implements Resolve<ILigneCmdClient> {
  constructor(protected service: LigneCmdClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneCmdClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ligneCmdClient: HttpResponse<LigneCmdClient>) => {
          if (ligneCmdClient.body) {
            return of(ligneCmdClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneCmdClient());
  }
}
