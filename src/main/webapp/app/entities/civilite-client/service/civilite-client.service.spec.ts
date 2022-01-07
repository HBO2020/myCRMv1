import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICiviliteClient, CiviliteClient } from '../civilite-client.model';

import { CiviliteClientService } from './civilite-client.service';

describe('CiviliteClient Service', () => {
  let service: CiviliteClientService;
  let httpMock: HttpTestingController;
  let elemDefault: ICiviliteClient;
  let expectedResult: ICiviliteClient | ICiviliteClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CiviliteClientService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      civiliteLibelleCl: 'AAAAAAA',
      civiliteCodeCl: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CiviliteClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CiviliteClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CiviliteClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          civiliteLibelleCl: 'BBBBBB',
          civiliteCodeCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CiviliteClient', () => {
      const patchObject = Object.assign(
        {
          civiliteCodeCl: 1,
        },
        new CiviliteClient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CiviliteClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          civiliteLibelleCl: 'BBBBBB',
          civiliteCodeCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CiviliteClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCiviliteClientToCollectionIfMissing', () => {
      it('should add a CiviliteClient to an empty array', () => {
        const civiliteClient: ICiviliteClient = { id: 123 };
        expectedResult = service.addCiviliteClientToCollectionIfMissing([], civiliteClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civiliteClient);
      });

      it('should not add a CiviliteClient to an array that contains it', () => {
        const civiliteClient: ICiviliteClient = { id: 123 };
        const civiliteClientCollection: ICiviliteClient[] = [
          {
            ...civiliteClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addCiviliteClientToCollectionIfMissing(civiliteClientCollection, civiliteClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CiviliteClient to an array that doesn't contain it", () => {
        const civiliteClient: ICiviliteClient = { id: 123 };
        const civiliteClientCollection: ICiviliteClient[] = [{ id: 456 }];
        expectedResult = service.addCiviliteClientToCollectionIfMissing(civiliteClientCollection, civiliteClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civiliteClient);
      });

      it('should add only unique CiviliteClient to an array', () => {
        const civiliteClientArray: ICiviliteClient[] = [{ id: 123 }, { id: 456 }, { id: 96930 }];
        const civiliteClientCollection: ICiviliteClient[] = [{ id: 123 }];
        expectedResult = service.addCiviliteClientToCollectionIfMissing(civiliteClientCollection, ...civiliteClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const civiliteClient: ICiviliteClient = { id: 123 };
        const civiliteClient2: ICiviliteClient = { id: 456 };
        expectedResult = service.addCiviliteClientToCollectionIfMissing([], civiliteClient, civiliteClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civiliteClient);
        expect(expectedResult).toContain(civiliteClient2);
      });

      it('should accept null and undefined values', () => {
        const civiliteClient: ICiviliteClient = { id: 123 };
        expectedResult = service.addCiviliteClientToCollectionIfMissing([], null, civiliteClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civiliteClient);
      });

      it('should return initial array if no CiviliteClient is added', () => {
        const civiliteClientCollection: ICiviliteClient[] = [{ id: 123 }];
        expectedResult = service.addCiviliteClientToCollectionIfMissing(civiliteClientCollection, undefined, null);
        expect(expectedResult).toEqual(civiliteClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
