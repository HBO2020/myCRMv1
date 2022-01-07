import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LivraisonFrService } from '../service/livraison-fr.service';
import { ILivraisonFr, LivraisonFr } from '../livraison-fr.model';

import { LivraisonFrUpdateComponent } from './livraison-fr-update.component';

describe('LivraisonFr Management Update Component', () => {
  let comp: LivraisonFrUpdateComponent;
  let fixture: ComponentFixture<LivraisonFrUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let livraisonFrService: LivraisonFrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LivraisonFrUpdateComponent],
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
      .overrideTemplate(LivraisonFrUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LivraisonFrUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    livraisonFrService = TestBed.inject(LivraisonFrService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const livraisonFr: ILivraisonFr = { id: 456 };

      activatedRoute.data = of({ livraisonFr });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(livraisonFr));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonFr>>();
      const livraisonFr = { id: 123 };
      jest.spyOn(livraisonFrService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonFr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: livraisonFr }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(livraisonFrService.update).toHaveBeenCalledWith(livraisonFr);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonFr>>();
      const livraisonFr = new LivraisonFr();
      jest.spyOn(livraisonFrService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonFr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: livraisonFr }));
      saveSubject.complete();

      // THEN
      expect(livraisonFrService.create).toHaveBeenCalledWith(livraisonFr);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LivraisonFr>>();
      const livraisonFr = { id: 123 };
      jest.spyOn(livraisonFrService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ livraisonFr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(livraisonFrService.update).toHaveBeenCalledWith(livraisonFr);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
