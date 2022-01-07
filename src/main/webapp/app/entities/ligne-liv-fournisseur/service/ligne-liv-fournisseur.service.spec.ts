import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILigneLivFournisseur, LigneLivFournisseur } from '../ligne-liv-fournisseur.model';

import { LigneLivFournisseurService } from './ligne-liv-fournisseur.service';

describe('LigneLivFournisseur Service', () => {
  let service: LigneLivFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: ILigneLivFournisseur;
  let expectedResult: ILigneLivFournisseur | ILigneLivFournisseur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LigneLivFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      livFrQuantite: 0,
      livFrNmPieces: 0,
      livFrTotalPrix: 0,
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

    it('should create a LigneLivFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LigneLivFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LigneLivFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          livFrQuantite: 1,
          livFrNmPieces: 1,
          livFrTotalPrix: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LigneLivFournisseur', () => {
      const patchObject = Object.assign(
        {
          livFrQuantite: 1,
          livFrNmPieces: 1,
        },
        new LigneLivFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LigneLivFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          livFrQuantite: 1,
          livFrNmPieces: 1,
          livFrTotalPrix: 1,
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

    it('should delete a LigneLivFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLigneLivFournisseurToCollectionIfMissing', () => {
      it('should add a LigneLivFournisseur to an empty array', () => {
        const ligneLivFournisseur: ILigneLivFournisseur = { id: 123 };
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing([], ligneLivFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneLivFournisseur);
      });

      it('should not add a LigneLivFournisseur to an array that contains it', () => {
        const ligneLivFournisseur: ILigneLivFournisseur = { id: 123 };
        const ligneLivFournisseurCollection: ILigneLivFournisseur[] = [
          {
            ...ligneLivFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing(ligneLivFournisseurCollection, ligneLivFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LigneLivFournisseur to an array that doesn't contain it", () => {
        const ligneLivFournisseur: ILigneLivFournisseur = { id: 123 };
        const ligneLivFournisseurCollection: ILigneLivFournisseur[] = [{ id: 456 }];
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing(ligneLivFournisseurCollection, ligneLivFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneLivFournisseur);
      });

      it('should add only unique LigneLivFournisseur to an array', () => {
        const ligneLivFournisseurArray: ILigneLivFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 85398 }];
        const ligneLivFournisseurCollection: ILigneLivFournisseur[] = [{ id: 123 }];
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing(ligneLivFournisseurCollection, ...ligneLivFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ligneLivFournisseur: ILigneLivFournisseur = { id: 123 };
        const ligneLivFournisseur2: ILigneLivFournisseur = { id: 456 };
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing([], ligneLivFournisseur, ligneLivFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneLivFournisseur);
        expect(expectedResult).toContain(ligneLivFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const ligneLivFournisseur: ILigneLivFournisseur = { id: 123 };
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing([], null, ligneLivFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneLivFournisseur);
      });

      it('should return initial array if no LigneLivFournisseur is added', () => {
        const ligneLivFournisseurCollection: ILigneLivFournisseur[] = [{ id: 123 }];
        expectedResult = service.addLigneLivFournisseurToCollectionIfMissing(ligneLivFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(ligneLivFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
