import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPayementFournisseur, PayementFournisseur } from '../payement-fournisseur.model';
import { PayementFournisseurService } from '../service/payement-fournisseur.service';

import { PayementFournisseurRoutingResolveService } from './payement-fournisseur-routing-resolve.service';

describe('PayementFournisseur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PayementFournisseurRoutingResolveService;
  let service: PayementFournisseurService;
  let resultPayementFournisseur: IPayementFournisseur | undefined;

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
    routingResolveService = TestBed.inject(PayementFournisseurRoutingResolveService);
    service = TestBed.inject(PayementFournisseurService);
    resultPayementFournisseur = undefined;
  });

  describe('resolve', () => {
    it('should return IPayementFournisseur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayementFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPayementFournisseur).toEqual({ id: 123 });
    });

    it('should return new IPayementFournisseur if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayementFournisseur = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPayementFournisseur).toEqual(new PayementFournisseur());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PayementFournisseur })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPayementFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPayementFournisseur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
