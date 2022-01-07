import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILigneCmdClient, LigneCmdClient } from '../ligne-cmd-client.model';

import { LigneCmdClientService } from './ligne-cmd-client.service';

describe('LigneCmdClient Service', () => {
  let service: LigneCmdClientService;
  let httpMock: HttpTestingController;
  let elemDefault: ILigneCmdClient;
  let expectedResult: ILigneCmdClient | ILigneCmdClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LigneCmdClientService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      cmdQnCl: 0,
      cmdNmPiecesCl: 0,
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

    it('should create a LigneCmdClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LigneCmdClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LigneCmdClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cmdQnCl: 1,
          cmdNmPiecesCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LigneCmdClient', () => {
      const patchObject = Object.assign(
        {
          cmdQnCl: 1,
        },
        new LigneCmdClient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LigneCmdClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cmdQnCl: 1,
          cmdNmPiecesCl: 1,
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

    it('should delete a LigneCmdClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLigneCmdClientToCollectionIfMissing', () => {
      it('should add a LigneCmdClient to an empty array', () => {
        const ligneCmdClient: ILigneCmdClient = { id: 123 };
        expectedResult = service.addLigneCmdClientToCollectionIfMissing([], ligneCmdClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneCmdClient);
      });

      it('should not add a LigneCmdClient to an array that contains it', () => {
        const ligneCmdClient: ILigneCmdClient = { id: 123 };
        const ligneCmdClientCollection: ILigneCmdClient[] = [
          {
            ...ligneCmdClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addLigneCmdClientToCollectionIfMissing(ligneCmdClientCollection, ligneCmdClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LigneCmdClient to an array that doesn't contain it", () => {
        const ligneCmdClient: ILigneCmdClient = { id: 123 };
        const ligneCmdClientCollection: ILigneCmdClient[] = [{ id: 456 }];
        expectedResult = service.addLigneCmdClientToCollectionIfMissing(ligneCmdClientCollection, ligneCmdClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneCmdClient);
      });

      it('should add only unique LigneCmdClient to an array', () => {
        const ligneCmdClientArray: ILigneCmdClient[] = [{ id: 123 }, { id: 456 }, { id: 29374 }];
        const ligneCmdClientCollection: ILigneCmdClient[] = [{ id: 123 }];
        expectedResult = service.addLigneCmdClientToCollectionIfMissing(ligneCmdClientCollection, ...ligneCmdClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ligneCmdClient: ILigneCmdClient = { id: 123 };
        const ligneCmdClient2: ILigneCmdClient = { id: 456 };
        expectedResult = service.addLigneCmdClientToCollectionIfMissing([], ligneCmdClient, ligneCmdClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneCmdClient);
        expect(expectedResult).toContain(ligneCmdClient2);
      });

      it('should accept null and undefined values', () => {
        const ligneCmdClient: ILigneCmdClient = { id: 123 };
        expectedResult = service.addLigneCmdClientToCollectionIfMissing([], null, ligneCmdClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneCmdClient);
      });

      it('should return initial array if no LigneCmdClient is added', () => {
        const ligneCmdClientCollection: ILigneCmdClient[] = [{ id: 123 }];
        expectedResult = service.addLigneCmdClientToCollectionIfMissing(ligneCmdClientCollection, undefined, null);
        expect(expectedResult).toEqual(ligneCmdClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
