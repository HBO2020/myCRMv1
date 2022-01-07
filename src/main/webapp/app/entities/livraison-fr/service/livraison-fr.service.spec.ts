import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILivraisonFr, LivraisonFr } from '../livraison-fr.model';

import { LivraisonFrService } from './livraison-fr.service';

describe('LivraisonFr Service', () => {
  let service: LivraisonFrService;
  let httpMock: HttpTestingController;
  let elemDefault: ILivraisonFr;
  let expectedResult: ILivraisonFr | ILivraisonFr[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LivraisonFrService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      bonLivIdent: 0,
      livFrDate: currentDate,
      livFrDateUpdate: currentDate,
      livDateEffet: currentDate,
      bonLivTotal: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          livFrDate: currentDate.format(DATE_FORMAT),
          livFrDateUpdate: currentDate.format(DATE_FORMAT),
          livDateEffet: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LivraisonFr', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          livFrDate: currentDate.format(DATE_FORMAT),
          livFrDateUpdate: currentDate.format(DATE_FORMAT),
          livDateEffet: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livFrDate: currentDate,
          livFrDateUpdate: currentDate,
          livDateEffet: currentDate,
        },
        returnedFromService
      );

      service.create(new LivraisonFr()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LivraisonFr', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bonLivIdent: 1,
          livFrDate: currentDate.format(DATE_FORMAT),
          livFrDateUpdate: currentDate.format(DATE_FORMAT),
          livDateEffet: currentDate.format(DATE_FORMAT),
          bonLivTotal: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livFrDate: currentDate,
          livFrDateUpdate: currentDate,
          livDateEffet: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LivraisonFr', () => {
      const patchObject = Object.assign(
        {
          bonLivIdent: 1,
          livFrDate: currentDate.format(DATE_FORMAT),
          livDateEffet: currentDate.format(DATE_FORMAT),
        },
        new LivraisonFr()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          livFrDate: currentDate,
          livFrDateUpdate: currentDate,
          livDateEffet: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LivraisonFr', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bonLivIdent: 1,
          livFrDate: currentDate.format(DATE_FORMAT),
          livFrDateUpdate: currentDate.format(DATE_FORMAT),
          livDateEffet: currentDate.format(DATE_FORMAT),
          bonLivTotal: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livFrDate: currentDate,
          livFrDateUpdate: currentDate,
          livDateEffet: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LivraisonFr', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLivraisonFrToCollectionIfMissing', () => {
      it('should add a LivraisonFr to an empty array', () => {
        const livraisonFr: ILivraisonFr = { id: 123 };
        expectedResult = service.addLivraisonFrToCollectionIfMissing([], livraisonFr);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(livraisonFr);
      });

      it('should not add a LivraisonFr to an array that contains it', () => {
        const livraisonFr: ILivraisonFr = { id: 123 };
        const livraisonFrCollection: ILivraisonFr[] = [
          {
            ...livraisonFr,
          },
          { id: 456 },
        ];
        expectedResult = service.addLivraisonFrToCollectionIfMissing(livraisonFrCollection, livraisonFr);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LivraisonFr to an array that doesn't contain it", () => {
        const livraisonFr: ILivraisonFr = { id: 123 };
        const livraisonFrCollection: ILivraisonFr[] = [{ id: 456 }];
        expectedResult = service.addLivraisonFrToCollectionIfMissing(livraisonFrCollection, livraisonFr);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(livraisonFr);
      });

      it('should add only unique LivraisonFr to an array', () => {
        const livraisonFrArray: ILivraisonFr[] = [{ id: 123 }, { id: 456 }, { id: 52220 }];
        const livraisonFrCollection: ILivraisonFr[] = [{ id: 123 }];
        expectedResult = service.addLivraisonFrToCollectionIfMissing(livraisonFrCollection, ...livraisonFrArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const livraisonFr: ILivraisonFr = { id: 123 };
        const livraisonFr2: ILivraisonFr = { id: 456 };
        expectedResult = service.addLivraisonFrToCollectionIfMissing([], livraisonFr, livraisonFr2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(livraisonFr);
        expect(expectedResult).toContain(livraisonFr2);
      });

      it('should accept null and undefined values', () => {
        const livraisonFr: ILivraisonFr = { id: 123 };
        expectedResult = service.addLivraisonFrToCollectionIfMissing([], null, livraisonFr, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(livraisonFr);
      });

      it('should return initial array if no LivraisonFr is added', () => {
        const livraisonFrCollection: ILivraisonFr[] = [{ id: 123 }];
        expectedResult = service.addLivraisonFrToCollectionIfMissing(livraisonFrCollection, undefined, null);
        expect(expectedResult).toEqual(livraisonFrCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
