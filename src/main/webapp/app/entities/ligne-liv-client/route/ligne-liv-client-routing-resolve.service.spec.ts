import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILigneLivClient, LigneLivClient } from '../ligne-liv-client.model';
import { LigneLivClientService } from '../service/ligne-liv-client.service';

import { LigneLivClientRoutingResolveService } from './ligne-liv-client-routing-resolve.service';

describe('LigneLivClient routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LigneLivClientRoutingResolveService;
  let service: LigneLivClientService;
  let resultLigneLivClient: ILigneLivClient | undefined;

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
    routingResolveService = TestBed.inject(LigneLivClientRoutingResolveService);
    service = TestBed.inject(LigneLivClientService);
    resultLigneLivClient = undefined;
  });

  describe('resolve', () => {
    it('should return ILigneLivClient returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneLivClient).toEqual({ id: 123 });
    });

    it('should return new ILigneLivClient if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivClient = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLigneLivClient).toEqual(new LigneLivClient());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LigneLivClient })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLigneLivClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLigneLivClient).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
