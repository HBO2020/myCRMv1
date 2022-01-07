import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUniteArticle, getUniteArticleIdentifier } from '../unite-article.model';

export type EntityResponseType = HttpResponse<IUniteArticle>;
export type EntityArrayResponseType = HttpResponse<IUniteArticle[]>;

@Injectable({ providedIn: 'root' })
export class UniteArticleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/unite-articles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(uniteArticle: IUniteArticle): Observable<EntityResponseType> {
    return this.http.post<IUniteArticle>(this.resourceUrl, uniteArticle, { observe: 'response' });
  }

  update(uniteArticle: IUniteArticle): Observable<EntityResponseType> {
    return this.http.put<IUniteArticle>(`${this.resourceUrl}/${getUniteArticleIdentifier(uniteArticle) as number}`, uniteArticle, {
      observe: 'response',
    });
  }

  partialUpdate(uniteArticle: IUniteArticle): Observable<EntityResponseType> {
    return this.http.patch<IUniteArticle>(`${this.resourceUrl}/${getUniteArticleIdentifier(uniteArticle) as number}`, uniteArticle, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniteArticle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniteArticle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUniteArticleToCollectionIfMissing(
    uniteArticleCollection: IUniteArticle[],
    ...uniteArticlesToCheck: (IUniteArticle | null | undefined)[]
  ): IUniteArticle[] {
    const uniteArticles: IUniteArticle[] = uniteArticlesToCheck.filter(isPresent);
    if (uniteArticles.length > 0) {
      const uniteArticleCollectionIdentifiers = uniteArticleCollection.map(
        uniteArticleItem => getUniteArticleIdentifier(uniteArticleItem)!
      );
      const uniteArticlesToAdd = uniteArticles.filter(uniteArticleItem => {
        const uniteArticleIdentifier = getUniteArticleIdentifier(uniteArticleItem);
        if (uniteArticleIdentifier == null || uniteArticleCollectionIdentifiers.includes(uniteArticleIdentifier)) {
          return false;
        }
        uniteArticleCollectionIdentifiers.push(uniteArticleIdentifier);
        return true;
      });
      return [...uniteArticlesToAdd, ...uniteArticleCollection];
    }
    return uniteArticleCollection;
  }
}
