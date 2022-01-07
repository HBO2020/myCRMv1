import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILivraisonCl, getLivraisonClIdentifier } from '../livraison-cl.model';

export type EntityResponseType = HttpResponse<ILivraisonCl>;
export type EntityArrayResponseType = HttpResponse<ILivraisonCl[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonClService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/livraison-cls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(livraisonCl: ILivraisonCl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonCl);
    return this.http
      .post<ILivraisonCl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraisonCl: ILivraisonCl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonCl);
    return this.http
      .put<ILivraisonCl>(`${this.resourceUrl}/${getLivraisonClIdentifier(livraisonCl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(livraisonCl: ILivraisonCl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonCl);
    return this.http
      .patch<ILivraisonCl>(`${this.resourceUrl}/${getLivraisonClIdentifier(livraisonCl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraisonCl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraisonCl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLivraisonClToCollectionIfMissing(
    livraisonClCollection: ILivraisonCl[],
    ...livraisonClsToCheck: (ILivraisonCl | null | undefined)[]
  ): ILivraisonCl[] {
    const livraisonCls: ILivraisonCl[] = livraisonClsToCheck.filter(isPresent);
    if (livraisonCls.length > 0) {
      const livraisonClCollectionIdentifiers = livraisonClCollection.map(livraisonClItem => getLivraisonClIdentifier(livraisonClItem)!);
      const livraisonClsToAdd = livraisonCls.filter(livraisonClItem => {
        const livraisonClIdentifier = getLivraisonClIdentifier(livraisonClItem);
        if (livraisonClIdentifier == null || livraisonClCollectionIdentifiers.includes(livraisonClIdentifier)) {
          return false;
        }
        livraisonClCollectionIdentifiers.push(livraisonClIdentifier);
        return true;
      });
      return [...livraisonClsToAdd, ...livraisonClCollection];
    }
    return livraisonClCollection;
  }

  protected convertDateFromClient(livraisonCl: ILivraisonCl): ILivraisonCl {
    return Object.assign({}, livraisonCl, {
      livDateCl: livraisonCl.livDateCl?.isValid() ? livraisonCl.livDateCl.format(DATE_FORMAT) : undefined,
      livDateUpdateCl: livraisonCl.livDateUpdateCl?.isValid() ? livraisonCl.livDateUpdateCl.format(DATE_FORMAT) : undefined,
      livDateEffetCl: livraisonCl.livDateEffetCl?.isValid() ? livraisonCl.livDateEffetCl.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.livDateCl = res.body.livDateCl ? dayjs(res.body.livDateCl) : undefined;
      res.body.livDateUpdateCl = res.body.livDateUpdateCl ? dayjs(res.body.livDateUpdateCl) : undefined;
      res.body.livDateEffetCl = res.body.livDateEffetCl ? dayjs(res.body.livDateEffetCl) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraisonCl: ILivraisonCl) => {
        livraisonCl.livDateCl = livraisonCl.livDateCl ? dayjs(livraisonCl.livDateCl) : undefined;
        livraisonCl.livDateUpdateCl = livraisonCl.livDateUpdateCl ? dayjs(livraisonCl.livDateUpdateCl) : undefined;
        livraisonCl.livDateEffetCl = livraisonCl.livDateEffetCl ? dayjs(livraisonCl.livDateEffetCl) : undefined;
      });
    }
    return res;
  }
}
