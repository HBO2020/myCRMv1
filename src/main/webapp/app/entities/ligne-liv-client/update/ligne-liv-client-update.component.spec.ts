import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LigneLivClientService } from '../service/ligne-liv-client.service';
import { ILigneLivClient, LigneLivClient } from '../ligne-liv-client.model';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';
import { LivraisonClService } from 'app/entities/livraison-cl/service/livraison-cl.service';

import { LigneLivClientUpdateComponent } from './ligne-liv-client-update.component';

describe('LigneLivClient Management Update Component', () => {
  let comp: LigneLivClientUpdateComponent;
  let fixture: ComponentFixture<LigneLivClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ligneLivClientService: LigneLivClientService;
  let livraisonClService: LivraisonClService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LigneLivClientUpdateComponent],
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
      .overrideTemplate(LigneLivClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneLivClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ligneLivClientService = TestBed.inject(LigneLivClientService);
    livraisonClService = TestBed.inject(LivraisonClService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LivraisonCl query and add missing value', () => {
      const ligneLivClient: ILigneLivClient = { id: 456 };
      const livraisonCl: ILivraisonCl = { id: 75659 };
      ligneLivClient.livraisonCl = livraisonCl;

      const livraisonClCollection: ILivraisonCl[] = [{ id: 10287 }];
      jest.spyOn(livraisonClService, 'query').mockReturnValue(of(new HttpResponse({ body: livraisonClCollection })));
      const additionalLivraisonCls = [livraisonCl];
      const expectedCollection: ILivraisonCl[] = [...additionalLivraisonCls, ...livraisonClCollection];
      jest.spyOn(livraisonClService, 'addLivraisonClToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneLivClient });
      comp.ngOnInit();

      expect(livraisonClService.query).toHaveBeenCalled();
      expect(livraisonClService.addLivraisonClToCollectionIfMissing).toHaveBeenCalledWith(livraisonClCollection, ...additionalLivraisonCls);
      expect(comp.livraisonClsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ligneLivClient: ILigneLivClient = { id: 456 };
      const livraisonCl: ILivraisonCl = { id: 97431 };
      ligneLivClient.livraisonCl = livraisonCl;

      activatedRoute.data = of({ ligneLivClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ligneLivClient));
      expect(comp.livraisonClsSharedCollection).toContain(livraisonCl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivClient>>();
      const ligneLivClient = { id: 123 };
      jest.spyOn(ligneLivClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneLivClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ligneLivClientService.update).toHaveBeenCalledWith(ligneLivClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivClient>>();
      const ligneLivClient = new LigneLivClient();
      jest.spyOn(ligneLivClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneLivClient }));
      saveSubject.complete();

      // THEN
      expect(ligneLivClientService.create).toHaveBeenCalledWith(ligneLivClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivClient>>();
      const ligneLivClient = { id: 123 };
      jest.spyOn(ligneLivClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ligneLivClientService.update).toHaveBeenCalledWith(ligneLivClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLivraisonClById', () => {
      it('Should return tracked LivraisonCl primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLivraisonClById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
