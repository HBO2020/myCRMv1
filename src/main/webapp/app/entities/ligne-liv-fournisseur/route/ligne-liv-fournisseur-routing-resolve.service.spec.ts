import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILigneLivFournisseur, LigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';

import { LigneLivFournisseurRoutingResolveService } from './ligne-liv-fournisseur-routing-resolve.service';

describe('LigneLivFournisseur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LigneLivFournisseurRoutingResolveService;
  let service: LigneLivFournisseurService;
  let resultLigneLivFournisseur: ILigneLivFournisseur | undefined;

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
    routingResolveService = TestBed.inject(LigneLivFournisseurRoutingResolveService);
    service = TestBed.inject(LigneLivFournisseurService);
    resultLigneLivFournisseur = undefined;
  });

  describe('resolve', () => {
    it('should return ILigneLivFournisseur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneLivFournisseur).toEqual({ id: 123 });
    });

    it('should return new ILigneLivFournisseur if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivFournisseur = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLigneLivFournisseur).toEqual(new LigneLivFournisseur());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LigneLivFournisseur })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneLivFournisseur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
