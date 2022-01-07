import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PayementFournisseurService } from '../service/payement-fournisseur.service';
import { IPayementFournisseur, PayementFournisseur } from '../payement-fournisseur.model';

import { PayementFournisseurUpdateComponent } from './payement-fournisseur-update.component';

describe('PayementFournisseur Management Update Component', () => {
  let comp: PayementFournisseurUpdateComponent;
  let fixture: ComponentFixture<PayementFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let payementFournisseurService: PayementFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PayementFournisseurUpdateComponent],
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
      .overrideTemplate(PayementFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayementFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    payementFournisseurService = TestBed.inject(PayementFournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const payementFournisseur: IPayementFournisseur = { id: 456 };

      activatedRoute.data = of({ payementFournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payementFournisseur));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementFournisseur>>();
      const payementFournisseur = { id: 123 };
      jest.spyOn(payementFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payementFournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(payementFournisseurService.update).toHaveBeenCalledWith(payementFournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementFournisseur>>();
      const payementFournisseur = new PayementFournisseur();
      jest.spyOn(payementFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payementFournisseur }));
      saveSubject.complete();

      // THEN
      expect(payementFournisseurService.create).toHaveBeenCalledWith(payementFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementFournisseur>>();
      const payementFournisseur = { id: 123 };
      jest.spyOn(payementFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(payementFournisseurService.update).toHaveBeenCalledWith(payementFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
