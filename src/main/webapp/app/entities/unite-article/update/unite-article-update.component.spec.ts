import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UniteArticleService } from '../service/unite-article.service';
import { IUniteArticle, UniteArticle } from '../unite-article.model';

import { UniteArticleUpdateComponent } from './unite-article-update.component';

describe('UniteArticle Management Update Component', () => {
  let comp: UniteArticleUpdateComponent;
  let fixture: ComponentFixture<UniteArticleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let uniteArticleService: UniteArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UniteArticleUpdateComponent],
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
      .overrideTemplate(UniteArticleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UniteArticleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    uniteArticleService = TestBed.inject(UniteArticleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const uniteArticle: IUniteArticle = { id: 456 };

      activatedRoute.data = of({ uniteArticle });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(uniteArticle));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UniteArticle>>();
      const uniteArticle = { id: 123 };
      jest.spyOn(uniteArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uniteArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uniteArticle }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(uniteArticleService.update).toHaveBeenCalledWith(uniteArticle);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UniteArticle>>();
      const uniteArticle = new UniteArticle();
      jest.spyOn(uniteArticleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uniteArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uniteArticle }));
      saveSubject.complete();

      // THEN
      expect(uniteArticleService.create).toHaveBeenCalledWith(uniteArticle);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UniteArticle>>();
      const uniteArticle = { id: 123 };
      jest.spyOn(uniteArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uniteArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(uniteArticleService.update).toHaveBeenCalledWith(uniteArticle);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
