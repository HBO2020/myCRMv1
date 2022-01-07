import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICiviliteClient, CiviliteClient } from '../civilite-client.model';
import { CiviliteClientService } from '../service/civilite-client.service';

import { CiviliteClientRoutingResolveService } from './civilite-client-routing-resolve.service';

describe('CiviliteClient routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CiviliteClientRoutingResolveService;
  let service: CiviliteClientService;
  let resultCiviliteClient: ICiviliteClient | undefined;

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
    routingResolveService = TestBed.inject(CiviliteClientRoutingResolveService);
    service = TestBed.inject(CiviliteClientService);
    resultCiviliteClient = undefined;
  });

  describe('resolve', () => {
    it('should return ICiviliteClient returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCiviliteClient).toEqual({ id: 123 });
    });

    it('should return new ICiviliteClient if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteClient = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCiviliteClient).toEqual(new CiviliteClient());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CiviliteClient })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCiviliteClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCiviliteClient).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
