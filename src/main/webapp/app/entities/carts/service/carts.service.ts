import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarts, getCartsIdentifier } from '../carts.model';

export type EntityResponseType = HttpResponse<ICarts>;
export type EntityArrayResponseType = HttpResponse<ICarts[]>;

@Injectable({ providedIn: 'root' })
export class CartsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(carts: ICarts): Observable<EntityResponseType> {
    return this.http.post<ICarts>(this.resourceUrl, carts, { observe: 'response' });
  }

  update(carts: ICarts): Observable<EntityResponseType> {
    return this.http.put<ICarts>(`${this.resourceUrl}/${getCartsIdentifier(carts) as number}`, carts, { observe: 'response' });
  }

  partialUpdate(carts: ICarts): Observable<EntityResponseType> {
    return this.http.patch<ICarts>(`${this.resourceUrl}/${getCartsIdentifier(carts) as number}`, carts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCartsToCollectionIfMissing(cartsCollection: ICarts[], ...cartsToCheck: (ICarts | null | undefined)[]): ICarts[] {
    const carts: ICarts[] = cartsToCheck.filter(isPresent);
    if (carts.length > 0) {
      const cartsCollectionIdentifiers = cartsCollection.map(cartsItem => getCartsIdentifier(cartsItem)!);
      const cartsToAdd = carts.filter(cartsItem => {
        const cartsIdentifier = getCartsIdentifier(cartsItem);
        if (cartsIdentifier == null || cartsCollectionIdentifiers.includes(cartsIdentifier)) {
          return false;
        }
        cartsCollectionIdentifiers.push(cartsIdentifier);
        return true;
      });
      return [...cartsToAdd, ...cartsCollection];
    }
    return cartsCollection;
  }
}
