import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICiviliteClient, getCiviliteClientIdentifier } from '../civilite-client.model';

export type EntityResponseType = HttpResponse<ICiviliteClient>;
export type EntityArrayResponseType = HttpResponse<ICiviliteClient[]>;

@Injectable({ providedIn: 'root' })
export class CiviliteClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/civilite-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(civiliteClient: ICiviliteClient): Observable<EntityResponseType> {
    return this.http.post<ICiviliteClient>(this.resourceUrl, civiliteClient, { observe: 'response' });
  }

  update(civiliteClient: ICiviliteClient): Observable<EntityResponseType> {
    return this.http.put<ICiviliteClient>(`${this.resourceUrl}/${getCiviliteClientIdentifier(civiliteClient) as number}`, civiliteClient, {
      observe: 'response',
    });
  }

  partialUpdate(civiliteClient: ICiviliteClient): Observable<EntityResponseType> {
    return this.http.patch<ICiviliteClient>(
      `${this.resourceUrl}/${getCiviliteClientIdentifier(civiliteClient) as number}`,
      civiliteClient,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICiviliteClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICiviliteClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCiviliteClientToCollectionIfMissing(
    civiliteClientCollection: ICiviliteClient[],
    ...civiliteClientsToCheck: (ICiviliteClient | null | undefined)[]
  ): ICiviliteClient[] {
    const civiliteClients: ICiviliteClient[] = civiliteClientsToCheck.filter(isPresent);
    if (civiliteClients.length > 0) {
      const civiliteClientCollectionIdentifiers = civiliteClientCollection.map(
        civiliteClientItem => getCiviliteClientIdentifier(civiliteClientItem)!
      );
      const civiliteClientsToAdd = civiliteClients.filter(civiliteClientItem => {
        const civiliteClientIdentifier = getCiviliteClientIdentifier(civiliteClientItem);
        if (civiliteClientIdentifier == null || civiliteClientCollectionIdentifiers.includes(civiliteClientIdentifier)) {
          return false;
        }
        civiliteClientCollectionIdentifiers.push(civiliteClientIdentifier);
        return true;
      });
      return [...civiliteClientsToAdd, ...civiliteClientCollection];
    }
    return civiliteClientCollection;
  }
}
