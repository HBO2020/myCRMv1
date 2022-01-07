import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CiviliteClientService } from '../service/civilite-client.service';
import { ICiviliteClient, CiviliteClient } from '../civilite-client.model';

import { CiviliteClientUpdateComponent } from './civilite-client-update.component';

describe('CiviliteClient Management Update Component', () => {
  let comp: CiviliteClientUpdateComponent;
  let fixture: ComponentFixture<CiviliteClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let civiliteClientService: CiviliteClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CiviliteClientUpdateComponent],
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
      .overrideTemplate(CiviliteClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CiviliteClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    civiliteClientService = TestBed.inject(CiviliteClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const civiliteClient: ICiviliteClient = { id: 456 };

      activatedRoute.data = of({ civiliteClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(civiliteClient));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteClient>>();
      const civiliteClient = { id: 123 };
      jest.spyOn(civiliteClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civiliteClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(civiliteClientService.update).toHaveBeenCalledWith(civiliteClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteClient>>();
      const civiliteClient = new CiviliteClient();
      jest.spyOn(civiliteClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civiliteClient }));
      saveSubject.complete();

      // THEN
      expect(civiliteClientService.create).toHaveBeenCalledWith(civiliteClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CiviliteClient>>();
      const civiliteClient = { id: 123 };
      jest.spyOn(civiliteClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civiliteClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(civiliteClientService.update).toHaveBeenCalledWith(civiliteClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
