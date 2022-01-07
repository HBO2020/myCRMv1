import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILigneLivClient, LigneLivClient } from '../ligne-liv-client.model';

import { LigneLivClientService } from './ligne-liv-client.service';

describe('LigneLivClient Service', () => {
  let service: LigneLivClientService;
  let httpMock: HttpTestingController;
  let elemDefault: ILigneLivClient;
  let expectedResult: ILigneLivClient | ILigneLivClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LigneLivClientService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      livQuantiteCl: 0,
      livNmPiecesCl: 0,
      livTotalPrixCl: 0,
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

    it('should create a LigneLivClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LigneLivClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LigneLivClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          livQuantiteCl: 1,
          livNmPiecesCl: 1,
          livTotalPrixCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LigneLivClient', () => {
      const patchObject = Object.assign(
        {
          livQuantiteCl: 1,
          livNmPiecesCl: 1,
          livTotalPrixCl: 1,
        },
        new LigneLivClient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LigneLivClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          livQuantiteCl: 1,
          livNmPiecesCl: 1,
          livTotalPrixCl: 1,
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

    it('should delete a LigneLivClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLigneLivClientToCollectionIfMissing', () => {
      it('should add a LigneLivClient to an empty array', () => {
        const ligneLivClient: ILigneLivClient = { id: 123 };
        expectedResult = service.addLigneLivClientToCollectionIfMissing([], ligneLivClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneLivClient);
      });

      it('should not add a LigneLivClient to an array that contains it', () => {
        const ligneLivClient: ILigneLivClient = { id: 123 };
        const ligneLivClientCollection: ILigneLivClient[] = [
          {
            ...ligneLivClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addLigneLivClientToCollectionIfMissing(ligneLivClientCollection, ligneLivClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LigneLivClient to an array that doesn't contain it", () => {
        const ligneLivClient: ILigneLivClient = { id: 123 };
        const ligneLivClientCollection: ILigneLivClient[] = [{ id: 456 }];
        expectedResult = service.addLigneLivClientToCollectionIfMissing(ligneLivClientCollection, ligneLivClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneLivClient);
      });

      it('should add only unique LigneLivClient to an array', () => {
        const ligneLivClientArray: ILigneLivClient[] = [{ id: 123 }, { id: 456 }, { id: 66469 }];
        const ligneLivClientCollection: ILigneLivClient[] = [{ id: 123 }];
        expectedResult = service.addLigneLivClientToCollectionIfMissing(ligneLivClientCollection, ...ligneLivClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ligneLivClient: ILigneLivClient = { id: 123 };
        const ligneLivClient2: ILigneLivClient = { id: 456 };
        expectedResult = service.addLigneLivClientToCollectionIfMissing([], ligneLivClient, ligneLivClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneLivClient);
        expect(expectedResult).toContain(ligneLivClient2);
      });

      it('should accept null and undefined values', () => {
        const ligneLivClient: ILigneLivClient = { id: 123 };
        expectedResult = service.addLigneLivClientToCollectionIfMissing([], null, ligneLivClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneLivClient);
      });

      it('should return initial array if no LigneLivClient is added', () => {
        const ligneLivClientCollection: ILigneLivClient[] = [{ id: 123 }];
        expectedResult = service.addLigneLivClientToCollectionIfMissing(ligneLivClientCollection, undefined, null);
        expect(expectedResult).toEqual(ligneLivClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
