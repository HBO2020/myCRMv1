import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FournisseurService } from '../service/fournisseur.service';
import { IFournisseur, Fournisseur } from '../fournisseur.model';
import { ICiviliteFournisseur } from 'app/entities/civilite-fournisseur/civilite-fournisseur.model';
import { CiviliteFournisseurService } from 'app/entities/civilite-fournisseur/service/civilite-fournisseur.service';

import { FournisseurUpdateComponent } from './fournisseur-update.component';

describe('Fournisseur Management Update Component', () => {
  let comp: FournisseurUpdateComponent;
  let fixture: ComponentFixture<FournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fournisseurService: FournisseurService;
  let civiliteFournisseurService: CiviliteFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FournisseurUpdateComponent],
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
      .overrideTemplate(FournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fournisseurService = TestBed.inject(FournisseurService);
    civiliteFournisseurService = TestBed.inject(CiviliteFournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CiviliteFournisseur query and add missing value', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const civilitefr: ICiviliteFournisseur = { id: 3565 };
      fournisseur.civilitefr = civilitefr;

      const civiliteFournisseurCollection: ICiviliteFournisseur[] = [{ id: 78473 }];
      jest.spyOn(civiliteFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: civiliteFournisseurCollection })));
      const additionalCiviliteFournisseurs = [civilitefr];
      const expectedCollection: ICiviliteFournisseur[] = [...additionalCiviliteFournisseurs, ...civiliteFournisseurCollection];
      jest.spyOn(civiliteFournisseurService, 'addCiviliteFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(civiliteFournisseurService.query).toHaveBeenCalled();
      expect(civiliteFournisseurService.addCiviliteFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        civiliteFournisseurCollection,
        ...additionalCiviliteFournisseurs
      );
      expect(comp.civiliteFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const civilitefr: ICiviliteFournisseur = { id: 77915 };
      fournisseur.civilitefr = civilitefr;

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fournisseur));
      expect(comp.civiliteFournisseursSharedCollection).toContain(civilitefr);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fournisseurService.update).toHaveBeenCalledWith(fournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fournisseur>>();
      const fournisseur = new Fournisseur();
      jest.spyOn(fournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(fournisseurService.create).toHaveBeenCalledWith(fournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Fournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fournisseurService.update).toHaveBeenCalledWith(fournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCiviliteFournisseurById', () => {
      it('Should return tracked CiviliteFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCiviliteFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
