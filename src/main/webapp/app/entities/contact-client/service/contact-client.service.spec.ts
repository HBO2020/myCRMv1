import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContactClient, ContactClient } from '../contact-client.model';

import { ContactClientService } from './contact-client.service';

describe('ContactClient Service', () => {
  let service: ContactClientService;
  let httpMock: HttpTestingController;
  let elemDefault: IContactClient;
  let expectedResult: IContactClient | IContactClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactClientService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      contactNameCl: 'AAAAAAA',
      contactPrenomCl: 'AAAAAAA',
      contactEmailCl: 'AAAAAAA',
      contactMobilePhoneCl: 'AAAAAAA',
      contactStatusCl: 'AAAAAAA',
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

    it('should create a ContactClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ContactClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContactClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactNameCl: 'BBBBBB',
          contactPrenomCl: 'BBBBBB',
          contactEmailCl: 'BBBBBB',
          contactMobilePhoneCl: 'BBBBBB',
          contactStatusCl: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContactClient', () => {
      const patchObject = Object.assign(
        {
          contactPrenomCl: 'BBBBBB',
          contactMobilePhoneCl: 'BBBBBB',
          contactStatusCl: 'BBBBBB',
        },
        new ContactClient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContactClient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactNameCl: 'BBBBBB',
          contactPrenomCl: 'BBBBBB',
          contactEmailCl: 'BBBBBB',
          contactMobilePhoneCl: 'BBBBBB',
          contactStatusCl: 'BBBBBB',
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

    it('should delete a ContactClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContactClientToCollectionIfMissing', () => {
      it('should add a ContactClient to an empty array', () => {
        const contactClient: IContactClient = { id: 123 };
        expectedResult = service.addContactClientToCollectionIfMissing([], contactClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactClient);
      });

      it('should not add a ContactClient to an array that contains it', () => {
        const contactClient: IContactClient = { id: 123 };
        const contactClientCollection: IContactClient[] = [
          {
            ...contactClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addContactClientToCollectionIfMissing(contactClientCollection, contactClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContactClient to an array that doesn't contain it", () => {
        const contactClient: IContactClient = { id: 123 };
        const contactClientCollection: IContactClient[] = [{ id: 456 }];
        expectedResult = service.addContactClientToCollectionIfMissing(contactClientCollection, contactClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactClient);
      });

      it('should add only unique ContactClient to an array', () => {
        const contactClientArray: IContactClient[] = [{ id: 123 }, { id: 456 }, { id: 65230 }];
        const contactClientCollection: IContactClient[] = [{ id: 123 }];
        expectedResult = service.addContactClientToCollectionIfMissing(contactClientCollection, ...contactClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contactClient: IContactClient = { id: 123 };
        const contactClient2: IContactClient = { id: 456 };
        expectedResult = service.addContactClientToCollectionIfMissing([], contactClient, contactClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactClient);
        expect(expectedResult).toContain(contactClient2);
      });

      it('should accept null and undefined values', () => {
        const contactClient: IContactClient = { id: 123 };
        expectedResult = service.addContactClientToCollectionIfMissing([], null, contactClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactClient);
      });

      it('should return initial array if no ContactClient is added', () => {
        const contactClientCollection: IContactClient[] = [{ id: 123 }];
        expectedResult = service.addContactClientToCollectionIfMissing(contactClientCollection, undefined, null);
        expect(expectedResult).toEqual(contactClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
