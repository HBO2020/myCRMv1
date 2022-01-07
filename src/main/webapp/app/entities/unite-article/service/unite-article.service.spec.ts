import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUniteArticle, UniteArticle } from '../unite-article.model';

import { UniteArticleService } from './unite-article.service';

describe('UniteArticle Service', () => {
  let service: UniteArticleService;
  let httpMock: HttpTestingController;
  let elemDefault: IUniteArticle;
  let expectedResult: IUniteArticle | IUniteArticle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UniteArticleService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      uniteCode: 0,
      uniteLibelle: 'AAAAAAA',
      uniteOption: 'AAAAAAA',
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

    it('should create a UniteArticle', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new UniteArticle()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UniteArticle', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          uniteCode: 1,
          uniteLibelle: 'BBBBBB',
          uniteOption: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UniteArticle', () => {
      const patchObject = Object.assign(
        {
          uniteOption: 'BBBBBB',
        },
        new UniteArticle()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UniteArticle', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          uniteCode: 1,
          uniteLibelle: 'BBBBBB',
          uniteOption: 'BBBBBB',
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

    it('should delete a UniteArticle', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUniteArticleToCollectionIfMissing', () => {
      it('should add a UniteArticle to an empty array', () => {
        const uniteArticle: IUniteArticle = { id: 123 };
        expectedResult = service.addUniteArticleToCollectionIfMissing([], uniteArticle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uniteArticle);
      });

      it('should not add a UniteArticle to an array that contains it', () => {
        const uniteArticle: IUniteArticle = { id: 123 };
        const uniteArticleCollection: IUniteArticle[] = [
          {
            ...uniteArticle,
          },
          { id: 456 },
        ];
        expectedResult = service.addUniteArticleToCollectionIfMissing(uniteArticleCollection, uniteArticle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UniteArticle to an array that doesn't contain it", () => {
        const uniteArticle: IUniteArticle = { id: 123 };
        const uniteArticleCollection: IUniteArticle[] = [{ id: 456 }];
        expectedResult = service.addUniteArticleToCollectionIfMissing(uniteArticleCollection, uniteArticle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uniteArticle);
      });

      it('should add only unique UniteArticle to an array', () => {
        const uniteArticleArray: IUniteArticle[] = [{ id: 123 }, { id: 456 }, { id: 50433 }];
        const uniteArticleCollection: IUniteArticle[] = [{ id: 123 }];
        expectedResult = service.addUniteArticleToCollectionIfMissing(uniteArticleCollection, ...uniteArticleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const uniteArticle: IUniteArticle = { id: 123 };
        const uniteArticle2: IUniteArticle = { id: 456 };
        expectedResult = service.addUniteArticleToCollectionIfMissing([], uniteArticle, uniteArticle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uniteArticle);
        expect(expectedResult).toContain(uniteArticle2);
      });

      it('should accept null and undefined values', () => {
        const uniteArticle: IUniteArticle = { id: 123 };
        expectedResult = service.addUniteArticleToCollectionIfMissing([], null, uniteArticle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uniteArticle);
      });

      it('should return initial array if no UniteArticle is added', () => {
        const uniteArticleCollection: IUniteArticle[] = [{ id: 123 }];
        expectedResult = service.addUniteArticleToCollectionIfMissing(uniteArticleCollection, undefined, null);
        expect(expectedResult).toEqual(uniteArticleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
