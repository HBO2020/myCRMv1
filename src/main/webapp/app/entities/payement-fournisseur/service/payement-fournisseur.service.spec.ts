import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPayementFournisseur, PayementFournisseur } from '../payement-fournisseur.model';

import { PayementFournisseurService } from './payement-fournisseur.service';

describe('PayementFournisseur Service', () => {
  let service: PayementFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: IPayementFournisseur;
  let expectedResult: IPayementFournisseur | IPayementFournisseur[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PayementFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      payementFrIdent: 0,
      payementFrDate: currentDate,
      payementFrMode: 'AAAAAAA',
      payementFrEcheance: currentDate,
      payementFrMontant: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          payementFrDate: currentDate.format(DATE_FORMAT),
          payementFrEcheance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PayementFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          payementFrDate: currentDate.format(DATE_FORMAT),
          payementFrEcheance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementFrDate: currentDate,
          payementFrEcheance: currentDate,
        },
        returnedFromService
      );

      service.create(new PayementFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PayementFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payementFrIdent: 1,
          payementFrDate: currentDate.format(DATE_FORMAT),
          payementFrMode: 'BBBBBB',
          payementFrEcheance: currentDate.format(DATE_FORMAT),
          payementFrMontant: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementFrDate: currentDate,
          payementFrEcheance: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PayementFournisseur', () => {
      const patchObject = Object.assign(
        {
          payementFrIdent: 1,
        },
        new PayementFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          payementFrDate: currentDate,
          payementFrEcheance: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PayementFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          payementFrIdent: 1,
          payementFrDate: currentDate.format(DATE_FORMAT),
          payementFrMode: 'BBBBBB',
          payementFrEcheance: currentDate.format(DATE_FORMAT),
          payementFrMontant: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          payementFrDate: currentDate,
          payementFrEcheance: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PayementFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPayementFournisseurToCollectionIfMissing', () => {
      it('should add a PayementFournisseur to an empty array', () => {
        const payementFournisseur: IPayementFournisseur = { id: 123 };
        expectedResult = service.addPayementFournisseurToCollectionIfMissing([], payementFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payementFournisseur);
      });

      it('should not add a PayementFournisseur to an array that contains it', () => {
        const payementFournisseur: IPayementFournisseur = { id: 123 };
        const payementFournisseurCollection: IPayementFournisseur[] = [
          {
            ...payementFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addPayementFournisseurToCollectionIfMissing(payementFournisseurCollection, payementFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PayementFournisseur to an array that doesn't contain it", () => {
        const payementFournisseur: IPayementFournisseur = { id: 123 };
        const payementFournisseurCollection: IPayementFournisseur[] = [{ id: 456 }];
        expectedResult = service.addPayementFournisseurToCollectionIfMissing(payementFournisseurCollection, payementFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payementFournisseur);
      });

      it('should add only unique PayementFournisseur to an array', () => {
        const payementFournisseurArray: IPayementFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 61964 }];
        const payementFournisseurCollection: IPayementFournisseur[] = [{ id: 123 }];
        expectedResult = service.addPayementFournisseurToCollectionIfMissing(payementFournisseurCollection, ...payementFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const payementFournisseur: IPayementFournisseur = { id: 123 };
        const payementFournisseur2: IPayementFournisseur = { id: 456 };
        expectedResult = service.addPayementFournisseurToCollectionIfMissing([], payementFournisseur, payementFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payementFournisseur);
        expect(expectedResult).toContain(payementFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const payementFournisseur: IPayementFournisseur = { id: 123 };
        expectedResult = service.addPayementFournisseurToCollectionIfMissing([], null, payementFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payementFournisseur);
      });

      it('should return initial array if no PayementFournisseur is added', () => {
        const payementFournisseurCollection: IPayementFournisseur[] = [{ id: 123 }];
        expectedResult = service.addPayementFournisseurToCollectionIfMissing(payementFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(payementFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
