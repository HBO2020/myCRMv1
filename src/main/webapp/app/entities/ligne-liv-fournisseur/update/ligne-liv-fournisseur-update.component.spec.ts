import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';
import { ILigneLivFournisseur, LigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';
import { LivraisonFrService } from 'app/entities/livraison-fr/service/livraison-fr.service';

import { LigneLivFournisseurUpdateComponent } from './ligne-liv-fournisseur-update.component';

describe('LigneLivFournisseur Management Update Component', () => {
  let comp: LigneLivFournisseurUpdateComponent;
  let fixture: ComponentFixture<LigneLivFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ligneLivFournisseurService: LigneLivFournisseurService;
  let livraisonFrService: LivraisonFrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LigneLivFournisseurUpdateComponent],
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
      .overrideTemplate(LigneLivFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneLivFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ligneLivFournisseurService = TestBed.inject(LigneLivFournisseurService);
    livraisonFrService = TestBed.inject(LivraisonFrService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LivraisonFr query and add missing value', () => {
      const ligneLivFournisseur: ILigneLivFournisseur = { id: 456 };
      const livraisonFr: ILivraisonFr = { id: 54280 };
      ligneLivFournisseur.livraisonFr = livraisonFr;

      const livraisonFrCollection: ILivraisonFr[] = [{ id: 91943 }];
      jest.spyOn(livraisonFrService, 'query').mockReturnValue(of(new HttpResponse({ body: livraisonFrCollection })));
      const additionalLivraisonFrs = [livraisonFr];
      const expectedCollection: ILivraisonFr[] = [...additionalLivraisonFrs, ...livraisonFrCollection];
      jest.spyOn(livraisonFrService, 'addLivraisonFrToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneLivFournisseur });
      comp.ngOnInit();

      expect(livraisonFrService.query).toHaveBeenCalled();
      expect(livraisonFrService.addLivraisonFrToCollectionIfMissing).toHaveBeenCalledWith(livraisonFrCollection, ...additionalLivraisonFrs);
      expect(comp.livraisonFrsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ligneLivFournisseur: ILigneLivFournisseur = { id: 456 };
      const livraisonFr: ILivraisonFr = { id: 12305 };
      ligneLivFournisseur.livraisonFr = livraisonFr;

      activatedRoute.data = of({ ligneLivFournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ligneLivFournisseur));
      expect(comp.livraisonFrsSharedCollection).toContain(livraisonFr);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivFournisseur>>();
      const ligneLivFournisseur = { id: 123 };
      jest.spyOn(ligneLivFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneLivFournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ligneLivFournisseurService.update).toHaveBeenCalledWith(ligneLivFournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivFournisseur>>();
      const ligneLivFournisseur = new LigneLivFournisseur();
      jest.spyOn(ligneLivFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneLivFournisseur }));
      saveSubject.complete();

      // THEN
      expect(ligneLivFournisseurService.create).toHaveBeenCalledWith(ligneLivFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneLivFournisseur>>();
      const ligneLivFournisseur = { id: 123 };
      jest.spyOn(ligneLivFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneLivFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ligneLivFournisseurService.update).toHaveBeenCalledWith(ligneLivFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLivraisonFrById', () => {
      it('Should return tracked LivraisonFr primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLivraisonFrById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
