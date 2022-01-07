import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFactureVente, getFactureVenteIdentifier } from '../facture-vente.model';

export type EntityResponseType = HttpResponse<IFactureVente>;
export type EntityArrayResponseType = HttpResponse<IFactureVente[]>;

@Injectable({ providedIn: 'root' })
export class FactureVenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/facture-ventes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(factureVente: IFactureVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureVente);
    return this.http
      .post<IFactureVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(factureVente: IFactureVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureVente);
    return this.http
      .put<IFactureVente>(`${this.resourceUrl}/${getFactureVenteIdentifier(factureVente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(factureVente: IFactureVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureVente);
    return this.http
      .patch<IFactureVente>(`${this.resourceUrl}/${getFactureVenteIdentifier(factureVente) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFactureVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFactureVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFactureVenteToCollectionIfMissing(
    factureVenteCollection: IFactureVente[],
    ...factureVentesToCheck: (IFactureVente | null | undefined)[]
  ): IFactureVente[] {
    const factureVentes: IFactureVente[] = factureVentesToCheck.filter(isPresent);
    if (factureVentes.length > 0) {
      const factureVenteCollectionIdentifiers = factureVenteCollection.map(
        factureVenteItem => getFactureVenteIdentifier(factureVenteItem)!
      );
      const factureVentesToAdd = factureVentes.filter(factureVenteItem => {
        const factureVenteIdentifier = getFactureVenteIdentifier(factureVenteItem);
        if (factureVenteIdentifier == null || factureVenteCollectionIdentifiers.includes(factureVenteIdentifier)) {
          return false;
        }
        factureVenteCollectionIdentifiers.push(factureVenteIdentifier);
        return true;
      });
      return [...factureVentesToAdd, ...factureVenteCollection];
    }
    return factureVenteCollection;
  }

  protected convertDateFromClient(factureVente: IFactureVente): IFactureVente {
    return Object.assign({}, factureVente, {
      venteDateEffet: factureVente.venteDateEffet?.isValid() ? factureVente.venteDateEffet.format(DATE_FORMAT) : undefined,
      venteDateUpdate: factureVente.venteDateUpdate?.isValid() ? factureVente.venteDateUpdate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.venteDateEffet = res.body.venteDateEffet ? dayjs(res.body.venteDateEffet) : undefined;
      res.body.venteDateUpdate = res.body.venteDateUpdate ? dayjs(res.body.venteDateUpdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((factureVente: IFactureVente) => {
        factureVente.venteDateEffet = factureVente.venteDateEffet ? dayjs(factureVente.venteDateEffet) : undefined;
        factureVente.venteDateUpdate = factureVente.venteDateUpdate ? dayjs(factureVente.venteDateUpdate) : undefined;
      });
    }
    return res;
  }
}
