import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneLivClient, getLigneLivClientIdentifier } from '../ligne-liv-client.model';

export type EntityResponseType = HttpResponse<ILigneLivClient>;
export type EntityArrayResponseType = HttpResponse<ILigneLivClient[]>;

@Injectable({ providedIn: 'root' })
export class LigneLivClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-liv-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ligneLivClient: ILigneLivClient): Observable<EntityResponseType> {
    return this.http.post<ILigneLivClient>(this.resourceUrl, ligneLivClient, { observe: 'response' });
  }

  update(ligneLivClient: ILigneLivClient): Observable<EntityResponseType> {
    return this.http.put<ILigneLivClient>(`${this.resourceUrl}/${getLigneLivClientIdentifier(ligneLivClient) as number}`, ligneLivClient, {
      observe: 'response',
    });
  }

  partialUpdate(ligneLivClient: ILigneLivClient): Observable<EntityResponseType> {
    return this.http.patch<ILigneLivClient>(
      `${this.resourceUrl}/${getLigneLivClientIdentifier(ligneLivClient) as number}`,
      ligneLivClient,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneLivClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneLivClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLigneLivClientToCollectionIfMissing(
    ligneLivClientCollection: ILigneLivClient[],
    ...ligneLivClientsToCheck: (ILigneLivClient | null | undefined)[]
  ): ILigneLivClient[] {
    const ligneLivClients: ILigneLivClient[] = ligneLivClientsToCheck.filter(isPresent);
    if (ligneLivClients.length > 0) {
      const ligneLivClientCollectionIdentifiers = ligneLivClientCollection.map(
        ligneLivClientItem => getLigneLivClientIdentifier(ligneLivClientItem)!
      );
      const ligneLivClientsToAdd = ligneLivClients.filter(ligneLivClientItem => {
        const ligneLivClientIdentifier = getLigneLivClientIdentifier(ligneLivClientItem);
        if (ligneLivClientIdentifier == null || ligneLivClientCollectionIdentifiers.includes(ligneLivClientIdentifier)) {
          return false;
        }
        ligneLivClientCollectionIdentifiers.push(ligneLivClientIdentifier);
        return true;
      });
      return [...ligneLivClientsToAdd, ...ligneLivClientCollection];
    }
    return ligneLivClientCollection;
  }
}
