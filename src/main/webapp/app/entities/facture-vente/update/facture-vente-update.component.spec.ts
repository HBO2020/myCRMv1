import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FactureVenteService } from '../service/facture-vente.service';
import { IFactureVente, FactureVente } from '../facture-vente.model';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';
import { LivraisonClService } from 'app/entities/livraison-cl/service/livraison-cl.service';

import { FactureVenteUpdateComponent } from './facture-vente-update.component';

describe('FactureVente Management Update Component', () => {
  let comp: FactureVenteUpdateComponent;
  let fixture: ComponentFixture<FactureVenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factureVenteService: FactureVenteService;
  let livraisonClService: LivraisonClService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FactureVenteUpdateComponent],
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
      .overrideTemplate(FactureVenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureVenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factureVenteService = TestBed.inject(FactureVenteService);
    livraisonClService = TestBed.inject(LivraisonClService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LivraisonCl query and add missing value', () => {
      const factureVente: IFactureVente = { id: 456 };
      const livraisonCl: ILivraisonCl = { id: 6858 };
      factureVente.livraisonCl = livraisonCl;

      const livraisonClCollection: ILivraisonCl[] = [{ id: 82213 }];
      jest.spyOn(livraisonClService, 'query').mockReturnValue(of(new HttpResponse({ body: livraisonClCollection })));
      const additionalLivraisonCls = [livraisonCl];
      const expectedCollection: ILivraisonCl[] = [...additionalLivraisonCls, ...livraisonClCollection];
      jest.spyOn(livraisonClService, 'addLivraisonClToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureVente });
      comp.ngOnInit();

      expect(livraisonClService.query).toHaveBeenCalled();
      expect(livraisonClService.addLivraisonClToCollectionIfMissing).toHaveBeenCalledWith(livraisonClCollection, ...additionalLivraisonCls);
      expect(comp.livraisonClsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const factureVente: IFactureVente = { id: 456 };
      const livraisonCl: ILivraisonCl = { id: 89910 };
      factureVente.livraisonCl = livraisonCl;

      activatedRoute.data = of({ factureVente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(factureVente));
      expect(comp.livraisonClsSharedCollection).toContain(livraisonCl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureVente>>();
      const factureVente = { id: 123 };
      jest.spyOn(factureVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureVente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(factureVenteService.update).toHaveBeenCalledWith(factureVente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureVente>>();
      const factureVente = new FactureVente();
      jest.spyOn(factureVenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureVente }));
      saveSubject.complete();

      // THEN
      expect(factureVenteService.create).toHaveBeenCalledWith(factureVente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureVente>>();
      const factureVente = { id: 123 };
      jest.spyOn(factureVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factureVenteService.update).toHaveBeenCalledWith(factureVente);
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
