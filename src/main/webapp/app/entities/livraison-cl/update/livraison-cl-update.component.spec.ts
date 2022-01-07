import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LivraisonClService } from '../service/livraison-cl.service';
import { ILivraisonCl, LivraisonCl } from '../livraison-cl.model';

import { LivraisonClUpdateComponent } from './livraison-cl-update.component';

describe('LivraisonCl Management Update Component', () => {
  let comp: LivraisonClUpdateComponent;
  let fixture: ComponentFixture<LivraisonClUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let livraisonClService: LivraisonClService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LivraisonClUpdateComponent],
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
      .overrideTemplate(LivraisonClUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LivraisonClUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    livraisonClService = TestBed.inject(LivraisonClService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const livraisonCl: ILivraisonCl = { id: 456 };

      activatedRoute.data = of({ livraisonCl });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(livraisonCl));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonCl>>();
      const livraisonCl = { id: 123 };
      jest.spyOn(livraisonClService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonCl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: livraisonCl }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(livraisonClService.update).toHaveBeenCalledWith(livraisonCl);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonCl>>();
      const livraisonCl = new LivraisonCl();
      jest.spyOn(livraisonClService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonCl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: livraisonCl }));
      saveSubject.complete();

      // THEN
      expect(livraisonClService.create).toHaveBeenCalledWith(livraisonCl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonCl>>();
      const livraisonCl = { id: 123 };
      jest.spyOn(livraisonClService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonCl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(livraisonClService.update).toHaveBeenCalledWith(livraisonCl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
