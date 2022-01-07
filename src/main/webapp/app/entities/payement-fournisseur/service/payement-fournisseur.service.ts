import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayementFournisseur, getPayementFournisseurIdentifier } from '../payement-fournisseur.model';

export type EntityResponseType = HttpResponse<IPayementFournisseur>;
export type EntityArrayResponseType = HttpResponse<IPayementFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class PayementFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payement-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payementFournisseur: IPayementFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementFournisseur);
    return this.http
      .post<IPayementFournisseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payementFournisseur: IPayementFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementFournisseur);
    return this.http
      .put<IPayementFournisseur>(`${this.resourceUrl}/${getPayementFournisseurIdentifier(payementFournisseur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(payementFournisseur: IPayementFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementFournisseur);
    return this.http
      .patch<IPayementFournisseur>(`${this.resourceUrl}/${getPayementFournisseurIdentifier(payementFournisseur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayementFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayementFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPayementFournisseurToCollectionIfMissing(
    payementFournisseurCollection: IPayementFournisseur[],
    ...payementFournisseursToCheck: (IPayementFournisseur | null | undefined)[]
  ): IPayementFournisseur[] {
    const payementFournisseurs: IPayementFournisseur[] = payementFournisseursToCheck.filter(isPresent);
    if (payementFournisseurs.length > 0) {
      const payementFournisseurCollectionIdentifiers = payementFournisseurCollection.map(
        payementFournisseurItem => getPayementFournisseurIdentifier(payementFournisseurItem)!
      );
      const payementFournisseursToAdd = payementFournisseurs.filter(payementFournisseurItem => {
        const payementFournisseurIdentifier = getPayementFournisseurIdentifier(payementFournisseurItem);
        if (payementFournisseurIdentifier == null || payementFournisseurCollectionIdentifiers.includes(payementFournisseurIdentifier)) {
          return false;
        }
        payementFournisseurCollectionIdentifiers.push(payementFournisseurIdentifier);
        return true;
      });
      return [...payementFournisseursToAdd, ...payementFournisseurCollection];
    }
    return payementFournisseurCollection;
  }

  protected convertDateFromClient(payementFournisseur: IPayementFournisseur): IPayementFournisseur {
    return Object.assign({}, payementFournisseur, {
      payementFrDate: payementFournisseur.payementFrDate?.isValid() ? payementFournisseur.payementFrDate.format(DATE_FORMAT) : undefined,
      payementFrEcheance: payementFournisseur.payementFrEcheance?.isValid()
        ? payementFournisseur.payementFrEcheance.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.payementFrDate = res.body.payementFrDate ? dayjs(res.body.payementFrDate) : undefined;
      res.body.payementFrEcheance = res.body.payementFrEcheance ? dayjs(res.body.payementFrEcheance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payementFournisseur: IPayementFournisseur) => {
        payementFournisseur.payementFrDate = payementFournisseur.payementFrDate ? dayjs(payementFournisseur.payementFrDate) : undefined;
        payementFournisseur.payementFrEcheance = payementFournisseur.payementFrEcheance
          ? dayjs(payementFournisseur.payementFrEcheance)
          : undefined;
      });
    }
    return res;
  }
}
