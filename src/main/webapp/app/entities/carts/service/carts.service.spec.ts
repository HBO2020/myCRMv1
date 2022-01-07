import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarts, Carts } from '../carts.model';

import { CartsService } from './carts.service';

describe('Carts Service', () => {
  let service: CartsService;
  let httpMock: HttpTestingController;
  let elemDefault: ICarts;
  let expectedResult: ICarts | ICarts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CartsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      cartIsEmpty: false,
      cartUserEmail: 'AAAAAAA',
      cartListProduct: 'AAAAAAA',
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

    it('should create a Carts', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Carts()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Carts', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cartIsEmpty: true,
          cartUserEmail: 'BBBBBB',
          cartListProduct: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Carts', () => {
      const patchObject = Object.assign(
        {
          cartIsEmpty: true,
          cartUserEmail: 'BBBBBB',
        },
        new Carts()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Carts', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cartIsEmpty: true,
          cartUserEmail: 'BBBBBB',
          cartListProduct: 'BBBBBB',
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

    it('should delete a Carts', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCartsToCollectionIfMissing', () => {
      it('should add a Carts to an empty array', () => {
        const carts: ICarts = { id: 123 };
        expectedResult = service.addCartsToCollectionIfMissing([], carts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carts);
      });

      it('should not add a Carts to an array that contains it', () => {
        const carts: ICarts = { id: 123 };
        const cartsCollection: ICarts[] = [
          {
            ...carts,
          },
          { id: 456 },
        ];
        expectedResult = service.addCartsToCollectionIfMissing(cartsCollection, carts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Carts to an array that doesn't contain it", () => {
        const carts: ICarts = { id: 123 };
        const cartsCollection: ICarts[] = [{ id: 456 }];
        expectedResult = service.addCartsToCollectionIfMissing(cartsCollection, carts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carts);
      });

      it('should add only unique Carts to an array', () => {
        const cartsArray: ICarts[] = [{ id: 123 }, { id: 456 }, { id: 63266 }];
        const cartsCollection: ICarts[] = [{ id: 123 }];
        expectedResult = service.addCartsToCollectionIfMissing(cartsCollection, ...cartsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carts: ICarts = { id: 123 };
        const carts2: ICarts = { id: 456 };
        expectedResult = service.addCartsToCollectionIfMissing([], carts, carts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carts);
        expect(expectedResult).toContain(carts2);
      });

      it('should accept null and undefined values', () => {
        const carts: ICarts = { id: 123 };
        expectedResult = service.addCartsToCollectionIfMissing([], null, carts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carts);
      });

      it('should return initial array if no Carts is added', () => {
        const cartsCollection: ICarts[] = [{ id: 123 }];
        expectedResult = service.addCartsToCollectionIfMissing(cartsCollection, undefined, null);
        expect(expectedResult).toEqual(cartsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
