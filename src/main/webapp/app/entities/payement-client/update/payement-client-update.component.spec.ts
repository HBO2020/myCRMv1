import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PayementClientService } from '../service/payement-client.service';
import { IPayementClient, PayementClient } from '../payement-client.model';

import { PayementClientUpdateComponent } from './payement-client-update.component';

describe('PayementClient Management Update Component', () => {
  let comp: PayementClientUpdateComponent;
  let fixture: ComponentFixture<PayementClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let payementClientService: PayementClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PayementClientUpdateComponent],
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
      .overrideTemplate(PayementClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayementClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    payementClientService = TestBed.inject(PayementClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const payementClient: IPayementClient = { id: 456 };

      activatedRoute.data = of({ payementClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payementClient));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementClient>>();
      const payementClient = { id: 123 };
      jest.spyOn(payementClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payementClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(payementClientService.update).toHaveBeenCalledWith(payementClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementClient>>();
      const payementClient = new PayementClient();
      jest.spyOn(payementClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payementClient }));
      saveSubject.complete();

      // THEN
      expect(payementClientService.create).toHaveBeenCalledWith(payementClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PayementClient>>();
      const payementClient = { id: 123 };
      jest.spyOn(payementClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payementClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(payementClientService.update).toHaveBeenCalledWith(payementClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
