import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneCmdFournisseur, getLigneCmdFournisseurIdentifier } from '../ligne-cmd-fournisseur.model';

export type EntityResponseType = HttpResponse<ILigneCmdFournisseur>;
export type EntityArrayResponseType = HttpResponse<ILigneCmdFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class LigneCmdFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-cmd-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ligneCmdFournisseur: ILigneCmdFournisseur): Observable<EntityResponseType> {
    return this.http.post<ILigneCmdFournisseur>(this.resourceUrl, ligneCmdFournisseur, { observe: 'response' });
  }

  update(ligneCmdFournisseur: ILigneCmdFournisseur): Observable<EntityResponseType> {
    return this.http.put<ILigneCmdFournisseur>(
      `${this.resourceUrl}/${getLigneCmdFournisseurIdentifier(ligneCmdFournisseur) as number}`,
      ligneCmdFournisseur,
      { observe: 'response' }
    );
  }

  partialUpdate(ligneCmdFournisseur: ILigneCmdFournisseur): Observable<EntityResponseType> {
    return this.http.patch<ILigneCmdFournisseur>(
      `${this.resourceUrl}/${getLigneCmdFournisseurIdentifier(ligneCmdFournisseur) as number}`,
      ligneCmdFournisseur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneCmdFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneCmdFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLigneCmdFournisseurToCollectionIfMissing(
    ligneCmdFournisseurCollection: ILigneCmdFournisseur[],
    ...ligneCmdFournisseursToCheck: (ILigneCmdFournisseur | null | undefined)[]
  ): ILigneCmdFournisseur[] {
    const ligneCmdFournisseurs: ILigneCmdFournisseur[] = ligneCmdFournisseursToCheck.filter(isPresent);
    if (ligneCmdFournisseurs.length > 0) {
      const ligneCmdFournisseurCollectionIdentifiers = ligneCmdFournisseurCollection.map(
        ligneCmdFournisseurItem => getLigneCmdFournisseurIdentifier(ligneCmdFournisseurItem)!
      );
      const ligneCmdFournisseursToAdd = ligneCmdFournisseurs.filter(ligneCmdFournisseurItem => {
        const ligneCmdFournisseurIdentifier = getLigneCmdFournisseurIdentifier(ligneCmdFournisseurItem);
        if (ligneCmdFournisseurIdentifier == null || ligneCmdFournisseurCollectionIdentifiers.includes(ligneCmdFournisseurIdentifier)) {
          return false;
        }
        ligneCmdFournisseurCollectionIdentifiers.push(ligneCmdFournisseurIdentifier);
        return true;
      });
      return [...ligneCmdFournisseursToAdd, ...ligneCmdFournisseurCollection];
    }
    return ligneCmdFournisseurCollection;
  }
}
