import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CartsService } from '../service/carts.service';
import { ICarts, Carts } from '../carts.model';

import { CartsUpdateComponent } from './carts-update.component';

describe('Carts Management Update Component', () => {
  let comp: CartsUpdateComponent;
  let fixture: ComponentFixture<CartsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cartsService: CartsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CartsUpdateComponent],
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
      .overrideTemplate(CartsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CartsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cartsService = TestBed.inject(CartsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const carts: ICarts = { id: 456 };

      activatedRoute.data = of({ carts });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(carts));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carts>>();
      const carts = { id: 123 };
      jest.spyOn(cartsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carts }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cartsService.update).toHaveBeenCalledWith(carts);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carts>>();
      const carts = new Carts();
      jest.spyOn(cartsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carts }));
      saveSubject.complete();

      // THEN
      expect(cartsService.create).toHaveBeenCalledWith(carts);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Carts>>();
      const carts = { id: 123 };
      jest.spyOn(cartsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cartsService.update).toHaveBeenCalledWith(carts);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
