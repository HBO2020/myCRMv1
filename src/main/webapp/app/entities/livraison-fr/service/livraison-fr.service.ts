import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILivraisonFr, getLivraisonFrIdentifier } from '../livraison-fr.model';

export type EntityResponseType = HttpResponse<ILivraisonFr>;
export type EntityArrayResponseType = HttpResponse<ILivraisonFr[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonFrService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/livraison-frs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(livraisonFr: ILivraisonFr): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonFr);
    return this.http
      .post<ILivraisonFr>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraisonFr: ILivraisonFr): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonFr);
    return this.http
      .put<ILivraisonFr>(`${this.resourceUrl}/${getLivraisonFrIdentifier(livraisonFr) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(livraisonFr: ILivraisonFr): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonFr);
    return this.http
      .patch<ILivraisonFr>(`${this.resourceUrl}/${getLivraisonFrIdentifier(livraisonFr) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraisonFr>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraisonFr[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLivraisonFrToCollectionIfMissing(
    livraisonFrCollection: ILivraisonFr[],
    ...livraisonFrsToCheck: (ILivraisonFr | null | undefined)[]
  ): ILivraisonFr[] {
    const livraisonFrs: ILivraisonFr[] = livraisonFrsToCheck.filter(isPresent);
    if (livraisonFrs.length > 0) {
      const livraisonFrCollectionIdentifiers = livraisonFrCollection.map(livraisonFrItem => getLivraisonFrIdentifier(livraisonFrItem)!);
      const livraisonFrsToAdd = livraisonFrs.filter(livraisonFrItem => {
        const livraisonFrIdentifier = getLivraisonFrIdentifier(livraisonFrItem);
        if (livraisonFrIdentifier == null || livraisonFrCollectionIdentifiers.includes(livraisonFrIdentifier)) {
          return false;
        }
        livraisonFrCollectionIdentifiers.push(livraisonFrIdentifier);
        return true;
      });
      return [...livraisonFrsToAdd, ...livraisonFrCollection];
    }
    return livraisonFrCollection;
  }

  protected convertDateFromClient(livraisonFr: ILivraisonFr): ILivraisonFr {
    return Object.assign({}, livraisonFr, {
      livFrDate: livraisonFr.livFrDate?.isValid() ? livraisonFr.livFrDate.format(DATE_FORMAT) : undefined,
      livFrDateUpdate: livraisonFr.livFrDateUpdate?.isValid() ? livraisonFr.livFrDateUpdate.format(DATE_FORMAT) : undefined,
      livDateEffet: livraisonFr.livDateEffet?.isValid() ? livraisonFr.livDateEffet.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.livFrDate = res.body.livFrDate ? dayjs(res.body.livFrDate) : undefined;
      res.body.livFrDateUpdate = res.body.livFrDateUpdate ? dayjs(res.body.livFrDateUpdate) : undefined;
      res.body.livDateEffet = res.body.livDateEffet ? dayjs(res.body.livDateEffet) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraisonFr: ILivraisonFr) => {
        livraisonFr.livFrDate = livraisonFr.livFrDate ? dayjs(livraisonFr.livFrDate) : undefined;
        livraisonFr.livFrDateUpdate = livraisonFr.livFrDateUpdate ? dayjs(livraisonFr.livFrDateUpdate) : undefined;
        livraisonFr.livDateEffet = livraisonFr.livDateEffet ? dayjs(livraisonFr.livDateEffet) : undefined;
      });
    }
    return res;
  }
}
