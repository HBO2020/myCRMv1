import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneLivFournisseur, getLigneLivFournisseurIdentifier } from '../ligne-liv-fournisseur.model';

export type EntityResponseType = HttpResponse<ILigneLivFournisseur>;
export type EntityArrayResponseType = HttpResponse<ILigneLivFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class LigneLivFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-liv-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ligneLivFournisseur: ILigneLivFournisseur): Observable<EntityResponseType> {
    return this.http.post<ILigneLivFournisseur>(this.resourceUrl, ligneLivFournisseur, { observe: 'response' });
  }

  update(ligneLivFournisseur: ILigneLivFournisseur): Observable<EntityResponseType> {
    return this.http.put<ILigneLivFournisseur>(
      `${this.resourceUrl}/${getLigneLivFournisseurIdentifier(ligneLivFournisseur) as number}`,
      ligneLivFournisseur,
      { observe: 'response' }
    );
  }

  partialUpdate(ligneLivFournisseur: ILigneLivFournisseur): Observable<EntityResponseType> {
    return this.http.patch<ILigneLivFournisseur>(
      `${this.resourceUrl}/${getLigneLivFournisseurIdentifier(ligneLivFournisseur) as number}`,
      ligneLivFournisseur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneLivFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneLivFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLigneLivFournisseurToCollectionIfMissing(
    ligneLivFournisseurCollection: ILigneLivFournisseur[],
    ...ligneLivFournisseursToCheck: (ILigneLivFournisseur | null | undefined)[]
  ): ILigneLivFournisseur[] {
    const ligneLivFournisseurs: ILigneLivFournisseur[] = ligneLivFournisseursToCheck.filter(isPresent);
    if (ligneLivFournisseurs.length > 0) {
      const ligneLivFournisseurCollectionIdentifiers = ligneLivFournisseurCollection.map(
        ligneLivFournisseurItem => getLigneLivFournisseurIdentifier(ligneLivFournisseurItem)!
      );
      const ligneLivFournisseursToAdd = ligneLivFournisseurs.filter(ligneLivFournisseurItem => {
        const ligneLivFournisseurIdentifier = getLigneLivFournisseurIdentifier(ligneLivFournisseurItem);
        if (ligneLivFournisseurIdentifier == null || ligneLivFournisseurCollectionIdentifiers.includes(ligneLivFournisseurIdentifier)) {
          return false;
        }
        ligneLivFournisseurCollectionIdentifiers.push(ligneLivFournisseurIdentifier);
        return true;
      });
      return [...ligneLivFournisseursToAdd, ...ligneLivFournisseurCollection];
    }
    return ligneLivFournisseurCollection;
  }
}
