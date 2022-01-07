import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICiviliteFournisseur, CiviliteFournisseur } from '../civilite-fournisseur.model';
import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';

import { CiviliteFournisseurRoutingResolveService } from './civilite-fournisseur-routing-resolve.service';

describe('CiviliteFournisseur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CiviliteFournisseurRoutingResolveService;
  let service: CiviliteFournisseurService;
  let resultCiviliteFournisseur: ICiviliteFournisseur | undefined;

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
    routingResolveService = TestBed.inject(CiviliteFournisseurRoutingResolveService);
    service = TestBed.inject(CiviliteFournisseurService);
    resultCiviliteFournisseur = undefined;
  });

  describe('resolve', () => {
    it('should return ICiviliteFournisseur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCiviliteFournisseur).toEqual({ id: 123 });
    });

    it('should return new ICiviliteFournisseur if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteFournisseur = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCiviliteFournisseur).toEqual(new CiviliteFournisseur());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CiviliteFournisseur })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCiviliteFournisseur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
