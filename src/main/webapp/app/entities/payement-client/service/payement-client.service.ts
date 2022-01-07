import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayementClient, getPayementClientIdentifier } from '../payement-client.model';

export type EntityResponseType = HttpResponse<IPayementClient>;
export type EntityArrayResponseType = HttpResponse<IPayementClient[]>;

@Injectable({ providedIn: 'root' })
export class PayementClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payement-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payementClient: IPayementClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementClient);
    return this.http
      .post<IPayementClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payementClient: IPayementClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementClient);
    return this.http
      .put<IPayementClient>(`${this.resourceUrl}/${getPayementClientIdentifier(payementClient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(payementClient: IPayementClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payementClient);
    return this.http
      .patch<IPayementClient>(`${this.resourceUrl}/${getPayementClientIdentifier(payementClient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayementClient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayementClient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPayementClientToCollectionIfMissing(
    payementClientCollection: IPayementClient[],
    ...payementClientsToCheck: (IPayementClient | null | undefined)[]
  ): IPayementClient[] {
    const payementClients: IPayementClient[] = payementClientsToCheck.filter(isPresent);
    if (payementClients.length > 0) {
      const payementClientCollectionIdentifiers = payementClientCollection.map(
        payementClientItem => getPayementClientIdentifier(payementClientItem)!
      );
      const payementClientsToAdd = payementClients.filter(payementClientItem => {
        const payementClientIdentifier = getPayementClientIdentifier(payementClientItem);
        if (payementClientIdentifier == null || payementClientCollectionIdentifiers.includes(payementClientIdentifier)) {
          return false;
        }
        payementClientCollectionIdentifiers.push(payementClientIdentifier);
        return true;
      });
      return [...payementClientsToAdd, ...payementClientCollection];
    }
    return payementClientCollection;
  }

  protected convertDateFromClient(payementClient: IPayementClient): IPayementClient {
    return Object.assign({}, payementClient, {
      payementClDate: payementClient.payementClDate?.isValid() ? payementClient.payementClDate.format(DATE_FORMAT) : undefined,
      payementClEcheance: payementClient.payementClEcheance?.isValid() ? payementClient.payementClEcheance.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.payementClDate = res.body.payementClDate ? dayjs(res.body.payementClDate) : undefined;
      res.body.payementClEcheance = res.body.payementClEcheance ? dayjs(res.body.payementClEcheance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payementClient: IPayementClient) => {
        payementClient.payementClDate = payementClient.payementClDate ? dayjs(payementClient.payementClDate) : undefined;
        payementClient.payementClEcheance = payementClient.payementClEcheance ? dayjs(payementClient.payementClEcheance) : undefined;
      });
    }
    return res;
  }
}
