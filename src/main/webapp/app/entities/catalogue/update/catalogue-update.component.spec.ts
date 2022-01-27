import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CatalogueService } from '../service/catalogue.service';
import { ICatalogue, Catalogue } from '../catalogue.model';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';

import { CatalogueUpdateComponent } from './catalogue-update.component';

describe('Catalogue Management Update Component', () => {
  let comp: CatalogueUpdateComponent;
  let fixture: ComponentFixture<CatalogueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let catalogueService: CatalogueService;
  let bookService: BookService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CatalogueUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CatalogueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CatalogueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    catalogueService = TestBed.inject(CatalogueService);
    bookService = TestBed.inject(BookService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Book query and add missing value', () => {
      const catalogue: ICatalogue = { id: 456 };
      const books: IBook[] = [{ id: 75446 }];
      catalogue.books = books;

      const bookCollection: IBook[] = [{ id: 74418 }];
      jest.spyOn(bookService, 'query').mockReturnValue(of(new HttpResponse({ body: bookCollection })));
      const additionalBooks = [...books];
      const expectedCollection: IBook[] = [...additionalBooks, ...bookCollection];
      jest.spyOn(bookService, 'addBookToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ catalogue });
      comp.ngOnInit();

      expect(bookService.query).toHaveBeenCalled();
      expect(bookService.addBookToCollectionIfMissing).toHaveBeenCalledWith(bookCollection, ...additionalBooks);
      expect(comp.booksSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const catalogue: ICatalogue = { id: 456 };
      const books: IBook = { id: 24085 };
      catalogue.books = [books];

      activatedRoute.data = of({ catalogue });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(catalogue));
      expect(comp.booksSharedCollection).toContain(books);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Catalogue>>();
      const catalogue = { id: 123 };
      jest.spyOn(catalogueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ catalogue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: catalogue }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(catalogueService.update).toHaveBeenCalledWith(catalogue);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Catalogue>>();
      const catalogue = new Catalogue();
      jest.spyOn(catalogueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ catalogue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: catalogue }));
      saveSubject.complete();

      // THEN
      expect(catalogueService.create).toHaveBeenCalledWith(catalogue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Catalogue>>();
      const catalogue = { id: 123 };
      jest.spyOn(catalogueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ catalogue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(catalogueService.update).toHaveBeenCalledWith(catalogue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBookById', () => {
      it('Should return tracked Book primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBookById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedBook', () => {
      it('Should return option if no Book is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedBook(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Book for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedBook(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Book is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedBook(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
