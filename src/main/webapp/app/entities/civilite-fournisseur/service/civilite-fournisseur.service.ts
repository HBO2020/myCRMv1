import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICiviliteFournisseur, getCiviliteFournisseurIdentifier } from '../civilite-fournisseur.model';

export type EntityResponseType = HttpResponse<ICiviliteFournisseur>;
export type EntityArrayResponseType = HttpResponse<ICiviliteFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class CiviliteFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/civilite-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(civiliteFournisseur: ICiviliteFournisseur): Observable<EntityResponseType> {
    return this.http.post<ICiviliteFournisseur>(this.resourceUrl, civiliteFournisseur, { observe: 'response' });
  }

  update(civiliteFournisseur: ICiviliteFournisseur): Observable<EntityResponseType> {
    return this.http.put<ICiviliteFournisseur>(
      `${this.resourceUrl}/${getCiviliteFournisseurIdentifier(civiliteFournisseur) as number}`,
      civiliteFournisseur,
      { observe: 'response' }
    );
  }

  partialUpdate(civiliteFournisseur: ICiviliteFournisseur): Observable<EntityResponseType> {
    return this.http.patch<ICiviliteFournisseur>(
      `${this.resourceUrl}/${getCiviliteFournisseurIdentifier(civiliteFournisseur) as number}`,
      civiliteFournisseur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICiviliteFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICiviliteFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCiviliteFournisseurToCollectionIfMissing(
    civiliteFournisseurCollection: ICiviliteFournisseur[],
    ...civiliteFournisseursToCheck: (ICiviliteFournisseur | null | undefined)[]
  ): ICiviliteFournisseur[] {
    const civiliteFournisseurs: ICiviliteFournisseur[] = civiliteFournisseursToCheck.filter(isPresent);
    if (civiliteFournisseurs.length > 0) {
      const civiliteFournisseurCollectionIdentifiers = civiliteFournisseurCollection.map(
        civiliteFournisseurItem => getCiviliteFournisseurIdentifier(civiliteFournisseurItem)!
      );
      const civiliteFournisseursToAdd = civiliteFournisseurs.filter(civiliteFournisseurItem => {
        const civiliteFournisseurIdentifier = getCiviliteFournisseurIdentifier(civiliteFournisseurItem);
        if (civiliteFournisseurIdentifier == null || civiliteFournisseurCollectionIdentifiers.includes(civiliteFournisseurIdentifier)) {
          return false;
        }
        civiliteFournisseurCollectionIdentifiers.push(civiliteFournisseurIdentifier);
        return true;
      });
      return [...civiliteFournisseursToAdd, ...civiliteFournisseurCollection];
    }
    return civiliteFournisseurCollection;
  }
}
