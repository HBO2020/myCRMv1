import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContactClient, getContactClientIdentifier } from '../contact-client.model';

export type EntityResponseType = HttpResponse<IContactClient>;
export type EntityArrayResponseType = HttpResponse<IContactClient[]>;

@Injectable({ providedIn: 'root' })
export class ContactClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contact-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contactClient: IContactClient): Observable<EntityResponseType> {
    return this.http.post<IContactClient>(this.resourceUrl, contactClient, { observe: 'response' });
  }

  update(contactClient: IContactClient): Observable<EntityResponseType> {
    return this.http.put<IContactClient>(`${this.resourceUrl}/${getContactClientIdentifier(contactClient) as number}`, contactClient, {
      observe: 'response',
    });
  }

  partialUpdate(contactClient: IContactClient): Observable<EntityResponseType> {
    return this.http.patch<IContactClient>(`${this.resourceUrl}/${getContactClientIdentifier(contactClient) as number}`, contactClient, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContactClientToCollectionIfMissing(
    contactClientCollection: IContactClient[],
    ...contactClientsToCheck: (IContactClient | null | undefined)[]
  ): IContactClient[] {
    const contactClients: IContactClient[] = contactClientsToCheck.filter(isPresent);
    if (contactClients.length > 0) {
      const contactClientCollectionIdentifiers = contactClientCollection.map(
        contactClientItem => getContactClientIdentifier(contactClientItem)!
      );
      const contactClientsToAdd = contactClients.filter(contactClientItem => {
        const contactClientIdentifier = getContactClientIdentifier(contactClientItem);
        if (contactClientIdentifier == null || contactClientCollectionIdentifiers.includes(contactClientIdentifier)) {
          return false;
        }
        contactClientCollectionIdentifiers.push(contactClientIdentifier);
        return true;
      });
      return [...contactClientsToAdd, ...contactClientCollection];
    }
    return contactClientCollection;
  }
}
