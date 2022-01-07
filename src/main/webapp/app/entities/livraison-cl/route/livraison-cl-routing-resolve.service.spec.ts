import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILivraisonCl, LivraisonCl } from '../livraison-cl.model';
import { LivraisonClService } from '../service/livraison-cl.service';

import { LivraisonClRoutingResolveService } from './livraison-cl-routing-resolve.service';

describe('LivraisonCl routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LivraisonClRoutingResolveService;
  let service: LivraisonClService;
  let resultLivraisonCl: ILivraisonCl | undefined;

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
    routingResolveService = TestBed.inject(LivraisonClRoutingResolveService);
    service = TestBed.inject(LivraisonClService);
    resultLivraisonCl = undefined;
  });

  describe('resolve', () => {
    it('should return ILivraisonCl returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonCl = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLivraisonCl).toEqual({ id: 123 });
    });

    it('should return new ILivraisonCl if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonCl = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLivraisonCl).toEqual(new LivraisonCl());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LivraisonCl })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonCl = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLivraisonCl).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
