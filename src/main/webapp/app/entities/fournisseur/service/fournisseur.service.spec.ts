import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFournisseur, Fournisseur } from '../fournisseur.model';

import { FournisseurService } from './fournisseur.service';

describe('Fournisseur Service', () => {
  let service: FournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: IFournisseur;
  let expectedResult: IFournisseur | IFournisseur[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FournisseurService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      frIdent: 0,
      frRaisonSocial: 'AAAAAAA',
      frAdresse: 'AAAAAAA',
      frCodePostal: 'AAAAAAA',
      frVille: 'AAAAAAA',
      frCountry: 'AAAAAAA',
      frEmail: 'AAAAAAA',
      frNumeroMobile: 'AAAAAAA',
      frNumeroFax: 'AAAAAAA',
      frNumeroFixe: 'AAAAAAA',
      frDateCreation: currentDate,
      frDateUpdate: currentDate,
      frStatus: 'AAAAAAA',
      frNumeroSiret: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          frDateCreation: currentDate.format(DATE_FORMAT),
          frDateUpdate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Fournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          frDateCreation: currentDate.format(DATE_FORMAT),
          frDateUpdate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          frDateCreation: currentDate,
          frDateUpdate: currentDate,
        },
        returnedFromService
      );

      service.create(new Fournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Fournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          frIdent: 1,
          frRaisonSocial: 'BBBBBB',
          frAdresse: 'BBBBBB',
          frCodePostal: 'BBBBBB',
          frVille: 'BBBBBB',
          frCountry: 'BBBBBB',
          frEmail: 'BBBBBB',
          frNumeroMobile: 'BBBBBB',
          frNumeroFax: 'BBBBBB',
          frNumeroFixe: 'BBBBBB',
          frDateCreation: currentDate.format(DATE_FORMAT),
          frDateUpdate: currentDate.format(DATE_FORMAT),
          frStatus: 'BBBBBB',
          frNumeroSiret: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          frDateCreation: currentDate,
          frDateUpdate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Fournisseur', () => {
      const patchObject = Object.assign(
        {
          frRaisonSocial: 'BBBBBB',
          frAdresse: 'BBBBBB',
          frCodePostal: 'BBBBBB',
          frNumeroMobile: 'BBBBBB',
          frNumeroFixe: 'BBBBBB',
          frDateCreation: currentDate.format(DATE_FORMAT),
          frDateUpdate: currentDate.format(DATE_FORMAT),
          frNumeroSiret: 'BBBBBB',
        },
        new Fournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          frDateCreation: currentDate,
          frDateUpdate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Fournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          frIdent: 1,
          frRaisonSocial: 'BBBBBB',
          frAdresse: 'BBBBBB',
          frCodePostal: 'BBBBBB',
          frVille: 'BBBBBB',
          frCountry: 'BBBBBB',
          frEmail: 'BBBBBB',
          frNumeroMobile: 'BBBBBB',
          frNumeroFax: 'BBBBBB',
          frNumeroFixe: 'BBBBBB',
          frDateCreation: currentDate.format(DATE_FORMAT),
          frDateUpdate: currentDate.format(DATE_FORMAT),
          frStatus: 'BBBBBB',
          frNumeroSiret: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          frDateCreation: currentDate,
          frDateUpdate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Fournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFournisseurToCollectionIfMissing', () => {
      it('should add a Fournisseur to an empty array', () => {
        const fournisseur: IFournisseur = { id: 123 };
        expectedResult = service.addFournisseurToCollectionIfMissing([], fournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fournisseur);
      });

      it('should not add a Fournisseur to an array that contains it', () => {
        const fournisseur: IFournisseur = { id: 123 };
        const fournisseurCollection: IFournisseur[] = [
          {
            ...fournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addFournisseurToCollectionIfMissing(fournisseurCollection, fournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Fournisseur to an array that doesn't contain it", () => {
        const fournisseur: IFournisseur = { id: 123 };
        const fournisseurCollection: IFournisseur[] = [{ id: 456 }];
        expectedResult = service.addFournisseurToCollectionIfMissing(fournisseurCollection, fournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fournisseur);
      });

      it('should add only unique Fournisseur to an array', () => {
        const fournisseurArray: IFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 30666 }];
        const fournisseurCollection: IFournisseur[] = [{ id: 123 }];
        expectedResult = service.addFournisseurToCollectionIfMissing(fournisseurCollection, ...fournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fournisseur: IFournisseur = { id: 123 };
        const fournisseur2: IFournisseur = { id: 456 };
        expectedResult = service.addFournisseurToCollectionIfMissing([], fournisseur, fournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fournisseur);
        expect(expectedResult).toContain(fournisseur2);
      });

      it('should accept null and undefined values', () => {
        const fournisseur: IFournisseur = { id: 123 };
        expectedResult = service.addFournisseurToCollectionIfMissing([], null, fournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fournisseur);
      });

      it('should return initial array if no Fournisseur is added', () => {
        const fournisseurCollection: IFournisseur[] = [{ id: 123 }];
        expectedResult = service.addFournisseurToCollectionIfMissing(fournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(fournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
