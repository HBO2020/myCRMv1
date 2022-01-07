import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILigneCmdFournisseur, LigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';

import { LigneCmdFournisseurRoutingResolveService } from './ligne-cmd-fournisseur-routing-resolve.service';

describe('LigneCmdFournisseur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LigneCmdFournisseurRoutingResolveService;
  let service: LigneCmdFournisseurService;
  let resultLigneCmdFournisseur: ILigneCmdFournisseur | undefined;

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
    routingResolveService = TestBed.inject(LigneCmdFournisseurRoutingResolveService);
    service = TestBed.inject(LigneCmdFournisseurService);
    resultLigneCmdFournisseur = undefined;
  });

  describe('resolve', () => {
    it('should return ILigneCmdFournisseur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneCmdFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneCmdFournisseur).toEqual({ id: 123 });
    });

    it('should return new ILigneCmdFournisseur if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneCmdFournisseur = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLigneCmdFournisseur).toEqual(new LigneCmdFournisseur());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LigneCmdFournisseur })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneCmdFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneCmdFournisseur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
