import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFournisseur, getFournisseurIdentifier } from '../fournisseur.model';

export type EntityResponseType = HttpResponse<IFournisseur>;
export type EntityArrayResponseType = HttpResponse<IFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class FournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fournisseur: IFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournisseur);
    return this.http
      .post<IFournisseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fournisseur: IFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournisseur);
    return this.http
      .put<IFournisseur>(`${this.resourceUrl}/${getFournisseurIdentifier(fournisseur) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fournisseur: IFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fournisseur);
    return this.http
      .patch<IFournisseur>(`${this.resourceUrl}/${getFournisseurIdentifier(fournisseur) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFournisseurToCollectionIfMissing(
    fournisseurCollection: IFournisseur[],
    ...fournisseursToCheck: (IFournisseur | null | undefined)[]
  ): IFournisseur[] {
    const fournisseurs: IFournisseur[] = fournisseursToCheck.filter(isPresent);
    if (fournisseurs.length > 0) {
      const fournisseurCollectionIdentifiers = fournisseurCollection.map(fournisseurItem => getFournisseurIdentifier(fournisseurItem)!);
      const fournisseursToAdd = fournisseurs.filter(fournisseurItem => {
        const fournisseurIdentifier = getFournisseurIdentifier(fournisseurItem);
        if (fournisseurIdentifier == null || fournisseurCollectionIdentifiers.includes(fournisseurIdentifier)) {
          return false;
        }
        fournisseurCollectionIdentifiers.push(fournisseurIdentifier);
        return true;
      });
      return [...fournisseursToAdd, ...fournisseurCollection];
    }
    return fournisseurCollection;
  }

  protected convertDateFromClient(fournisseur: IFournisseur): IFournisseur {
    return Object.assign({}, fournisseur, {
      frDateCreation: fournisseur.frDateCreation?.isValid() ? fournisseur.frDateCreation.format(DATE_FORMAT) : undefined,
      frDateUpdate: fournisseur.frDateUpdate?.isValid() ? fournisseur.frDateUpdate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.frDateCreation = res.body.frDateCreation ? dayjs(res.body.frDateCreation) : undefined;
      res.body.frDateUpdate = res.body.frDateUpdate ? dayjs(res.body.frDateUpdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fournisseur: IFournisseur) => {
        fournisseur.frDateCreation = fournisseur.frDateCreation ? dayjs(fournisseur.frDateCreation) : undefined;
        fournisseur.frDateUpdate = fournisseur.frDateUpdate ? dayjs(fournisseur.frDateUpdate) : undefined;
      });
    }
    return res;
  }
}
