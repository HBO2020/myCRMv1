import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';
import { ICiviliteFournisseur, CiviliteFournisseur } from '../civilite-fournisseur.model';

import { CiviliteFournisseurUpdateComponent } from './civilite-fournisseur-update.component';

describe('CiviliteFournisseur Management Update Component', () => {
  let comp: CiviliteFournisseurUpdateComponent;
  let fixture: ComponentFixture<CiviliteFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let civiliteFournisseurService: CiviliteFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CiviliteFournisseurUpdateComponent],
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
      .overrideTemplate(CiviliteFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CiviliteFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    civiliteFournisseurService = TestBed.inject(CiviliteFournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const civiliteFournisseur: ICiviliteFournisseur = { id: 456 };

      activatedRoute.data = of({ civiliteFournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(civiliteFournisseur));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteFournisseur>>();
      const civiliteFournisseur = { id: 123 };
      jest.spyOn(civiliteFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civiliteFournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(civiliteFournisseurService.update).toHaveBeenCalledWith(civiliteFournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteFournisseur>>();
      const civiliteFournisseur = new CiviliteFournisseur();
      jest.spyOn(civiliteFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civiliteFournisseur }));
      saveSubject.complete();

      // THEN
      expect(civiliteFournisseurService.create).toHaveBeenCalledWith(civiliteFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteFournisseur>>();
      const civiliteFournisseur = { id: 123 };
      jest.spyOn(civiliteFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(civiliteFournisseurService.update).toHaveBeenCalledWith(civiliteFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
