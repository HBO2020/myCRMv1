import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPayementClient, PayementClient } from '../payement-client.model';

import { PayementClientService } from './payement-client.service';

describe('PayementClient Service', () => {
  let service: PayementClientService;
  let httpMock: HttpTestingController;
  let elemDefault: IPayementClient;
  let expectedResult: IPayementClient | IPayementClient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PayementClientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      payementClIdent: 0,
      payementClDate: currentDate,
      payementClMode: 'AAAAAAA',
      payementClEcheance: currentDate,
      payementClMontant: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          payementClDate: currentDate.format(DATE_FORMAT),
          payementClEcheance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PayementClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          payementClDate: currentDate.format(DATE_FORMAT),
          payementClEcheance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementClDate: currentDate,
          payementClEcheance: currentDate,
        },
        returnedFromService
      );

      service.create(new PayementClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PayementClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payementClIdent: 1,
          payementClDate: currentDate.format(DATE_FORMAT),
          payementClMode: 'BBBBBB',
          payementClEcheance: currentDate.format(DATE_FORMAT),
          payementClMontant: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementClDate: currentDate,
          payementClEcheance: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PayementClient', () => {
      const patchObject = Object.assign(
        {
          payementClIdent: 1,
          payementClDate: currentDate.format(DATE_FORMAT),
          payementClMode: 'BBBBBB',
          payementClMontant: 1,
        },
        new PayementClient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          payementClDate: currentDate,
          payementClEcheance: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PayementClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payementClIdent: 1,
          payementClDate: currentDate.format(DATE_FORMAT),
          payementClMode: 'BBBBBB',
          payementClEcheance: currentDate.format(DATE_FORMAT),
          payementClMontant: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementClDate: currentDate,
          payementClEcheance: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PayementClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPayementClientToCollectionIfMissing', () => {
      it('should add a PayementClient to an empty array', () => {
        const payementClient: IPayementClient = { id: 123 };
        expectedResult = service.addPayementClientToCollectionIfMissing([], payementClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payementClient);
      });

      it('should not add a PayementClient to an array that contains it', () => {
        const payementClient: IPayementClient = { id: 123 };
        const payementClientCollection: IPayementClient[] = [
          {
            ...payementClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addPayementClientToCollectionIfMissing(payementClientCollection, payementClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PayementClient to an array that doesn't contain it", () => {
        const payementClient: IPayementClient = { id: 123 };
        const payementClientCollection: IPayementClient[] = [{ id: 456 }];
        expectedResult = service.addPayementClientToCollectionIfMissing(payementClientCollection, payementClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payementClient);
      });

      it('should add only unique PayementClient to an array', () => {
        const payementClientArray: IPayementClient[] = [{ id: 123 }, { id: 456 }, { id: 21164 }];
        const payementClientCollection: IPayementClient[] = [{ id: 123 }];
        expectedResult = service.addPayementClientToCollectionIfMissing(payementClientCollection, ...payementClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const payementClient: IPayementClient = { id: 123 };
        const payementClient2: IPayementClient = { id: 456 };
        expectedResult = service.addPayementClientToCollectionIfMissing([], payementClient, payementClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payementClient);
        expect(expectedResult).toContain(payementClient2);
      });

      it('should accept null and undefined values', () => {
        const payementClient: IPayementClient = { id: 123 };
        expectedResult = service.addPayementClientToCollectionIfMissing([], null, payementClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payementClient);
      });

      it('should return initial array if no PayementClient is added', () => {
        const payementClientCollection: IPayementClient[] = [{ id: 123 }];
        expectedResult = service.addPayementClientToCollectionIfMissing(payementClientCollection, undefined, null);
        expect(expectedResult).toEqual(payementClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
