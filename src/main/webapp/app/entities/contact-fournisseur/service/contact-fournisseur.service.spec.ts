import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContactFournisseur, ContactFournisseur } from '../contact-fournisseur.model';

import { ContactFournisseurService } from './contact-fournisseur.service';

describe('ContactFournisseur Service', () => {
  let service: ContactFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: IContactFournisseur;
  let expectedResult: IContactFournisseur | IContactFournisseur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      contactFrName: 'AAAAAAA',
      contactfrPrenom: 'AAAAAAA',
      contactFrEmail: 'AAAAAAA',
      contactFrMobilePhone: 'AAAAAAA',
      contactFrStatus: 'AAAAAAA',
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

    it('should create a ContactFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ContactFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContactFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactFrName: 'BBBBBB',
          contactfrPrenom: 'BBBBBB',
          contactFrEmail: 'BBBBBB',
          contactFrMobilePhone: 'BBBBBB',
          contactFrStatus: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContactFournisseur', () => {
      const patchObject = Object.assign(
        {
          contactFrName: 'BBBBBB',
          contactfrPrenom: 'BBBBBB',
          contactFrEmail: 'BBBBBB',
        },
        new ContactFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContactFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactFrName: 'BBBBBB',
          contactfrPrenom: 'BBBBBB',
          contactFrEmail: 'BBBBBB',
          contactFrMobilePhone: 'BBBBBB',
          contactFrStatus: 'BBBBBB',
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

    it('should delete a ContactFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContactFournisseurToCollectionIfMissing', () => {
      it('should add a ContactFournisseur to an empty array', () => {
        const contactFournisseur: IContactFournisseur = { id: 123 };
        expectedResult = service.addContactFournisseurToCollectionIfMissing([], contactFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactFournisseur);
      });

      it('should not add a ContactFournisseur to an array that contains it', () => {
        const contactFournisseur: IContactFournisseur = { id: 123 };
        const contactFournisseurCollection: IContactFournisseur[] = [
          {
            ...contactFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addContactFournisseurToCollectionIfMissing(contactFournisseurCollection, contactFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContactFournisseur to an array that doesn't contain it", () => {
        const contactFournisseur: IContactFournisseur = { id: 123 };
        const contactFournisseurCollection: IContactFournisseur[] = [{ id: 456 }];
        expectedResult = service.addContactFournisseurToCollectionIfMissing(contactFournisseurCollection, contactFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactFournisseur);
      });

      it('should add only unique ContactFournisseur to an array', () => {
        const contactFournisseurArray: IContactFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 78111 }];
        const contactFournisseurCollection: IContactFournisseur[] = [{ id: 123 }];
        expectedResult = service.addContactFournisseurToCollectionIfMissing(contactFournisseurCollection, ...contactFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contactFournisseur: IContactFournisseur = { id: 123 };
        const contactFournisseur2: IContactFournisseur = { id: 456 };
        expectedResult = service.addContactFournisseurToCollectionIfMissing([], contactFournisseur, contactFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactFournisseur);
        expect(expectedResult).toContain(contactFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const contactFournisseur: IContactFournisseur = { id: 123 };
        expectedResult = service.addContactFournisseurToCollectionIfMissing([], null, contactFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactFournisseur);
      });

      it('should return initial array if no ContactFournisseur is added', () => {
        const contactFournisseurCollection: IContactFournisseur[] = [{ id: 123 }];
        expectedResult = service.addContactFournisseurToCollectionIfMissing(contactFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(contactFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
