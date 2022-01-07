import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUniteArticle, UniteArticle } from '../unite-article.model';
import { UniteArticleService } from '../service/unite-article.service';

import { UniteArticleRoutingResolveService } from './unite-article-routing-resolve.service';

describe('UniteArticle routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UniteArticleRoutingResolveService;
  let service: UniteArticleService;
  let resultUniteArticle: IUniteArticle | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(UniteArticleRoutingResolveService);
    service = TestBed.inject(UniteArticleService);
    resultUniteArticle = undefined;
  });

  describe('resolve', () => {
    it('should return IUniteArticle returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUniteArticle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUniteArticle).toEqual({ id: 123 });
    });

    it('should return new IUniteArticle if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUniteArticle = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUniteArticle).toEqual(new UniteArticle());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UniteArticle })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUniteArticle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUniteArticle).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
