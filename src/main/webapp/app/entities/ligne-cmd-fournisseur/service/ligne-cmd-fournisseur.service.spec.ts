import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILigneCmdFournisseur, LigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';

import { LigneCmdFournisseurService } from './ligne-cmd-fournisseur.service';

describe('LigneCmdFournisseur Service', () => {
  let service: LigneCmdFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: ILigneCmdFournisseur;
  let expectedResult: ILigneCmdFournisseur | ILigneCmdFournisseur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LigneCmdFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      cmdQnFr: 0,
      cmdNmPieces: 0,
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

    it('should create a LigneCmdFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LigneCmdFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LigneCmdFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cmdQnFr: 1,
          cmdNmPieces: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LigneCmdFournisseur', () => {
      const patchObject = Object.assign(
        {
          cmdQnFr: 1,
          cmdNmPieces: 1,
        },
        new LigneCmdFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LigneCmdFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cmdQnFr: 1,
          cmdNmPieces: 1,
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

    it('should delete a LigneCmdFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLigneCmdFournisseurToCollectionIfMissing', () => {
      it('should add a LigneCmdFournisseur to an empty array', () => {
        const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 123 };
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing([], ligneCmdFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneCmdFournisseur);
      });

      it('should not add a LigneCmdFournisseur to an array that contains it', () => {
        const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 123 };
        const ligneCmdFournisseurCollection: ILigneCmdFournisseur[] = [
          {
            ...ligneCmdFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing(ligneCmdFournisseurCollection, ligneCmdFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LigneCmdFournisseur to an array that doesn't contain it", () => {
        const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 123 };
        const ligneCmdFournisseurCollection: ILigneCmdFournisseur[] = [{ id: 456 }];
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing(ligneCmdFournisseurCollection, ligneCmdFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneCmdFournisseur);
      });

      it('should add only unique LigneCmdFournisseur to an array', () => {
        const ligneCmdFournisseurArray: ILigneCmdFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 26276 }];
        const ligneCmdFournisseurCollection: ILigneCmdFournisseur[] = [{ id: 123 }];
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing(ligneCmdFournisseurCollection, ...ligneCmdFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 123 };
        const ligneCmdFournisseur2: ILigneCmdFournisseur = { id: 456 };
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing([], ligneCmdFournisseur, ligneCmdFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneCmdFournisseur);
        expect(expectedResult).toContain(ligneCmdFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 123 };
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing([], null, ligneCmdFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneCmdFournisseur);
      });

      it('should return initial array if no LigneCmdFournisseur is added', () => {
        const ligneCmdFournisseurCollection: ILigneCmdFournisseur[] = [{ id: 123 }];
        expectedResult = service.addLigneCmdFournisseurToCollectionIfMissing(ligneCmdFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(ligneCmdFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
