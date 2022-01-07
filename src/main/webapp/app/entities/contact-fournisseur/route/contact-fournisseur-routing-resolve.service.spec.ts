import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IContactFournisseur, ContactFournisseur } from '../contact-fournisseur.model';
import { ContactFournisseurService } from '../service/contact-fournisseur.service';

import { ContactFournisseurRoutingResolveService } from './contact-fournisseur-routing-resolve.service';

describe('ContactFournisseur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ContactFournisseurRoutingResolveService;
  let service: ContactFournisseurService;
  let resultContactFournisseur: IContactFournisseur | undefined;

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
    routingResolveService = TestBed.inject(ContactFournisseurRoutingResolveService);
    service = TestBed.inject(ContactFournisseurService);
    resultContactFournisseur = undefined;
  });

  describe('resolve', () => {
    it('should return IContactFournisseur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContactFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultContactFournisseur).toEqual({ id: 123 });
    });

    it('should return new IContactFournisseur if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContactFournisseur = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContactFournisseur).toEqual(new ContactFournisseur());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ContactFournisseur })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultContactFournisseur = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultContactFournisseur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
