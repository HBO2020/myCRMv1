import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneCmdClient, getLigneCmdClientIdentifier } from '../ligne-cmd-client.model';

export type EntityResponseType = HttpResponse<ILigneCmdClient>;
export type EntityArrayResponseType = HttpResponse<ILigneCmdClient[]>;

@Injectable({ providedIn: 'root' })
export class LigneCmdClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-cmd-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ligneCmdClient: ILigneCmdClient): Observable<EntityResponseType> {
    return this.http.post<ILigneCmdClient>(this.resourceUrl, ligneCmdClient, { observe: 'response' });
  }

  update(ligneCmdClient: ILigneCmdClient): Observable<EntityResponseType> {
    return this.http.put<ILigneCmdClient>(`${this.resourceUrl}/${getLigneCmdClientIdentifier(ligneCmdClient) as number}`, ligneCmdClient, {
      observe: 'response',
    });
  }

  partialUpdate(ligneCmdClient: ILigneCmdClient): Observable<EntityResponseType> {
    return this.http.patch<ILigneCmdClient>(
      `${this.resourceUrl}/${getLigneCmdClientIdentifier(ligneCmdClient) as number}`,
      ligneCmdClient,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneCmdClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneCmdClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLigneCmdClientToCollectionIfMissing(
    ligneCmdClientCollection: ILigneCmdClient[],
    ...ligneCmdClientsToCheck: (ILigneCmdClient | null | undefined)[]
  ): ILigneCmdClient[] {
    const ligneCmdClients: ILigneCmdClient[] = ligneCmdClientsToCheck.filter(isPresent);
    if (ligneCmdClients.length > 0) {
      const ligneCmdClientCollectionIdentifiers = ligneCmdClientCollection.map(
        ligneCmdClientItem => getLigneCmdClientIdentifier(ligneCmdClientItem)!
      );
      const ligneCmdClientsToAdd = ligneCmdClients.filter(ligneCmdClientItem => {
        const ligneCmdClientIdentifier = getLigneCmdClientIdentifier(ligneCmdClientItem);
        if (ligneCmdClientIdentifier == null || ligneCmdClientCollectionIdentifiers.includes(ligneCmdClientIdentifier)) {
          return false;
        }
        ligneCmdClientCollectionIdentifiers.push(ligneCmdClientIdentifier);
        return true;
      });
      return [...ligneCmdClientsToAdd, ...ligneCmdClientCollection];
    }
    return ligneCmdClientCollection;
  }
}
