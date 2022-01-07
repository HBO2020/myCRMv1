import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILivraisonFr, LivraisonFr } from '../livraison-fr.model';
import { LivraisonFrService } from '../service/livraison-fr.service';

import { LivraisonFrRoutingResolveService } from './livraison-fr-routing-resolve.service';

describe('LivraisonFr routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LivraisonFrRoutingResolveService;
  let service: LivraisonFrService;
  let resultLivraisonFr: ILivraisonFr | undefined;

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
    routingResolveService = TestBed.inject(LivraisonFrRoutingResolveService);
    service = TestBed.inject(LivraisonFrService);
    resultLivraisonFr = undefined;
  });

  describe('resolve', () => {
    it('should return ILivraisonFr returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonFr = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLivraisonFr).toEqual({ id: 123 });
    });

    it('should return new ILivraisonFr if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonFr = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLivraisonFr).toEqual(new LivraisonFr());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LivraisonFr })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLivraisonFr = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLivraisonFr).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
