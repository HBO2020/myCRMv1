import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContactFournisseur, getContactFournisseurIdentifier } from '../contact-fournisseur.model';

export type EntityResponseType = HttpResponse<IContactFournisseur>;
export type EntityArrayResponseType = HttpResponse<IContactFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class ContactFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contact-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contactFournisseur: IContactFournisseur): Observable<EntityResponseType> {
    return this.http.post<IContactFournisseur>(this.resourceUrl, contactFournisseur, { observe: 'response' });
  }

  update(contactFournisseur: IContactFournisseur): Observable<EntityResponseType> {
    return this.http.put<IContactFournisseur>(
      `${this.resourceUrl}/${getContactFournisseurIdentifier(contactFournisseur) as number}`,
      contactFournisseur,
      { observe: 'response' }
    );
  }

  partialUpdate(contactFournisseur: IContactFournisseur): Observable<EntityResponseType> {
    return this.http.patch<IContactFournisseur>(
      `${this.resourceUrl}/${getContactFournisseurIdentifier(contactFournisseur) as number}`,
      contactFournisseur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContactFournisseurToCollectionIfMissing(
    contactFournisseurCollection: IContactFournisseur[],
    ...contactFournisseursToCheck: (IContactFournisseur | null | undefined)[]
  ): IContactFournisseur[] {
    const contactFournisseurs: IContactFournisseur[] = contactFournisseursToCheck.filter(isPresent);
    if (contactFournisseurs.length > 0) {
      const contactFournisseurCollectionIdentifiers = contactFournisseurCollection.map(
        contactFournisseurItem => getContactFournisseurIdentifier(contactFournisseurItem)!
      );
      const contactFournisseursToAdd = contactFournisseurs.filter(contactFournisseurItem => {
        const contactFournisseurIdentifier = getContactFournisseurIdentifier(contactFournisseurItem);
        if (contactFournisseurIdentifier == null || contactFournisseurCollectionIdentifiers.includes(contactFournisseurIdentifier)) {
          return false;
        }
        contactFournisseurCollectionIdentifiers.push(contactFournisseurIdentifier);
        return true;
      });
      return [...contactFournisseursToAdd, ...contactFournisseurCollection];
    }
    return contactFournisseurCollection;
  }
}
