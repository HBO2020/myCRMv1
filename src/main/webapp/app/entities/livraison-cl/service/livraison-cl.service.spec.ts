import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILivraisonCl, LivraisonCl } from '../livraison-cl.model';

import { LivraisonClService } from './livraison-cl.service';

describe('LivraisonCl Service', () => {
  let service: LivraisonClService;
  let httpMock: HttpTestingController;
  let elemDefault: ILivraisonCl;
  let expectedResult: ILivraisonCl | ILivraisonCl[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LivraisonClService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      bonLivIdentCl: 0,
      livDateCl: currentDate,
      livDateUpdateCl: currentDate,
      livDateEffetCl: currentDate,
      bonLivTotalCl: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          livDateCl: currentDate.format(DATE_FORMAT),
          livDateUpdateCl: currentDate.format(DATE_FORMAT),
          livDateEffetCl: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LivraisonCl', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          livDateCl: currentDate.format(DATE_FORMAT),
          livDateUpdateCl: currentDate.format(DATE_FORMAT),
          livDateEffetCl: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livDateCl: currentDate,
          livDateUpdateCl: currentDate,
          livDateEffetCl: currentDate,
        },
        returnedFromService
      );

      service.create(new LivraisonCl()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LivraisonCl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bonLivIdentCl: 1,
          livDateCl: currentDate.format(DATE_FORMAT),
          livDateUpdateCl: currentDate.format(DATE_FORMAT),
          livDateEffetCl: currentDate.format(DATE_FORMAT),
          bonLivTotalCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livDateCl: currentDate,
          livDateUpdateCl: currentDate,
          livDateEffetCl: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LivraisonCl', () => {
      const patchObject = Object.assign(
        {
          bonLivIdentCl: 1,
          livDateCl: currentDate.format(DATE_FORMAT),
          livDateUpdateCl: currentDate.format(DATE_FORMAT),
          livDateEffetCl: currentDate.format(DATE_FORMAT),
          bonLivTotalCl: 1,
        },
        new LivraisonCl()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          livDateCl: currentDate,
          livDateUpdateCl: currentDate,
          livDateEffetCl: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LivraisonCl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bonLivIdentCl: 1,
          livDateCl: currentDate.format(DATE_FORMAT),
          livDateUpdateCl: currentDate.format(DATE_FORMAT),
          livDateEffetCl: currentDate.format(DATE_FORMAT),
          bonLivTotalCl: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          livDateCl: currentDate,
          livDateUpdateCl: currentDate,
          livDateEffetCl: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LivraisonCl', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLivraisonClToCollectionIfMissing', () => {
      it('should add a LivraisonCl to an empty array', () => {
        const livraisonCl: ILivraisonCl = { id: 123 };
        expectedResult = service.addLivraisonClToCollectionIfMissing([], livraisonCl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(livraisonCl);
      });

      it('should not add a LivraisonCl to an array that contains it', () => {
        const livraisonCl: ILivraisonCl = { id: 123 };
        const livraisonClCollection: ILivraisonCl[] = [
          {
            ...livraisonCl,
          },
          { id: 456 },
        ];
        expectedResult = service.addLivraisonClToCollectionIfMissing(livraisonClCollection, livraisonCl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LivraisonCl to an array that doesn't contain it", () => {
        const livraisonCl: ILivraisonCl = { id: 123 };
        const livraisonClCollection: ILivraisonCl[] = [{ id: 456 }];
        expectedResult = service.addLivraisonClToCollectionIfMissing(livraisonClCollection, livraisonCl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(livraisonCl);
      });

      it('should add only unique LivraisonCl to an array', () => {
        const livraisonClArray: ILivraisonCl[] = [{ id: 123 }, { id: 456 }, { id: 85509 }];
        const livraisonClCollection: ILivraisonCl[] = [{ id: 123 }];
        expectedResult = service.addLivraisonClToCollectionIfMissing(livraisonClCollection, ...livraisonClArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const livraisonCl: ILivraisonCl = { id: 123 };
        const livraisonCl2: ILivraisonCl = { id: 456 };
        expectedResult = service.addLivraisonClToCollectionIfMissing([], livraisonCl, livraisonCl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(livraisonCl);
        expect(expectedResult).toContain(livraisonCl2);
      });

      it('should accept null and undefined values', () => {
        const livraisonCl: ILivraisonCl = { id: 123 };
        expectedResult = service.addLivraisonClToCollectionIfMissing([], null, livraisonCl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(livraisonCl);
      });

      it('should return initial array if no LivraisonCl is added', () => {
        const livraisonClCollection: ILivraisonCl[] = [{ id: 123 }];
        expectedResult = service.addLivraisonClToCollectionIfMissing(livraisonClCollection, undefined, null);
        expect(expectedResult).toEqual(livraisonClCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
