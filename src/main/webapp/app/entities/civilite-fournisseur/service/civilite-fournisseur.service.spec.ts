import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICiviliteFournisseur, CiviliteFournisseur } from '../civilite-fournisseur.model';

import { CiviliteFournisseurService } from './civilite-fournisseur.service';

describe('CiviliteFournisseur Service', () => {
  let service: CiviliteFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: ICiviliteFournisseur;
  let expectedResult: ICiviliteFournisseur | ICiviliteFournisseur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CiviliteFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      civiliteFrLibelle: 'AAAAAAA',
      civiliteFrCode: 0,
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

    it('should create a CiviliteFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CiviliteFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CiviliteFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          civiliteFrLibelle: 'BBBBBB',
          civiliteFrCode: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CiviliteFournisseur', () => {
      const patchObject = Object.assign(
        {
          civiliteFrCode: 1,
        },
        new CiviliteFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CiviliteFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          civiliteFrLibelle: 'BBBBBB',
          civiliteFrCode: 1,
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

    it('should delete a CiviliteFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCiviliteFournisseurToCollectionIfMissing', () => {
      it('should add a CiviliteFournisseur to an empty array', () => {
        const civiliteFournisseur: ICiviliteFournisseur = { id: 123 };
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing([], civiliteFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civiliteFournisseur);
      });

      it('should not add a CiviliteFournisseur to an array that contains it', () => {
        const civiliteFournisseur: ICiviliteFournisseur = { id: 123 };
        const civiliteFournisseurCollection: ICiviliteFournisseur[] = [
          {
            ...civiliteFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing(civiliteFournisseurCollection, civiliteFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CiviliteFournisseur to an array that doesn't contain it", () => {
        const civiliteFournisseur: ICiviliteFournisseur = { id: 123 };
        const civiliteFournisseurCollection: ICiviliteFournisseur[] = [{ id: 456 }];
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing(civiliteFournisseurCollection, civiliteFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civiliteFournisseur);
      });

      it('should add only unique CiviliteFournisseur to an array', () => {
        const civiliteFournisseurArray: ICiviliteFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 6646 }];
        const civiliteFournisseurCollection: ICiviliteFournisseur[] = [{ id: 123 }];
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing(civiliteFournisseurCollection, ...civiliteFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const civiliteFournisseur: ICiviliteFournisseur = { id: 123 };
        const civiliteFournisseur2: ICiviliteFournisseur = { id: 456 };
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing([], civiliteFournisseur, civiliteFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civiliteFournisseur);
        expect(expectedResult).toContain(civiliteFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const civiliteFournisseur: ICiviliteFournisseur = { id: 123 };
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing([], null, civiliteFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civiliteFournisseur);
      });

      it('should return initial array if no CiviliteFournisseur is added', () => {
        const civiliteFournisseurCollection: ICiviliteFournisseur[] = [{ id: 123 }];
        expectedResult = service.addCiviliteFournisseurToCollectionIfMissing(civiliteFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(civiliteFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
