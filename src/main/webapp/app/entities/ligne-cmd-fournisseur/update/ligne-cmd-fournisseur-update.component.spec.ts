import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';
import { ILigneCmdFournisseur, LigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { CommandeFournisseurService } from 'app/entities/commande-fournisseur/service/commande-fournisseur.service';

import { LigneCmdFournisseurUpdateComponent } from './ligne-cmd-fournisseur-update.component';

describe('LigneCmdFournisseur Management Update Component', () => {
  let comp: LigneCmdFournisseurUpdateComponent;
  let fixture: ComponentFixture<LigneCmdFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ligneCmdFournisseurService: LigneCmdFournisseurService;
  let commandeFournisseurService: CommandeFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LigneCmdFournisseurUpdateComponent],
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
      .overrideTemplate(LigneCmdFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneCmdFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ligneCmdFournisseurService = TestBed.inject(LigneCmdFournisseurService);
    commandeFournisseurService = TestBed.inject(CommandeFournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CommandeFournisseur query and add missing value', () => {
      const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 456 };
      const commandeFourniseur: ICommandeFournisseur = { id: 59051 };
      ligneCmdFournisseur.commandeFourniseur = commandeFourniseur;

      const commandeFournisseurCollection: ICommandeFournisseur[] = [{ id: 58602 }];
      jest.spyOn(commandeFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: commandeFournisseurCollection })));
      const additionalCommandeFournisseurs = [commandeFourniseur];
      const expectedCollection: ICommandeFournisseur[] = [...additionalCommandeFournisseurs, ...commandeFournisseurCollection];
      jest.spyOn(commandeFournisseurService, 'addCommandeFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneCmdFournisseur });
      comp.ngOnInit();

      expect(commandeFournisseurService.query).toHaveBeenCalled();
      expect(commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        commandeFournisseurCollection,
        ...additionalCommandeFournisseurs
      );
      expect(comp.commandeFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 456 };
      const commandeFourniseur: ICommandeFournisseur = { id: 94006 };
      ligneCmdFournisseur.commandeFourniseur = commandeFourniseur;

      activatedRoute.data = of({ ligneCmdFournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ligneCmdFournisseur));
      expect(comp.commandeFournisseursSharedCollection).toContain(commandeFourniseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdFournisseur>>();
      const ligneCmdFournisseur = { id: 123 };
      jest.spyOn(ligneCmdFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneCmdFournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ligneCmdFournisseurService.update).toHaveBeenCalledWith(ligneCmdFournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdFournisseur>>();
      const ligneCmdFournisseur = new LigneCmdFournisseur();
      jest.spyOn(ligneCmdFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneCmdFournisseur }));
      saveSubject.complete();

      // THEN
      expect(ligneCmdFournisseurService.create).toHaveBeenCalledWith(ligneCmdFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdFournisseur>>();
      const ligneCmdFournisseur = { id: 123 };
      jest.spyOn(ligneCmdFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ligneCmdFournisseurService.update).toHaveBeenCalledWith(ligneCmdFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCommandeFournisseurById', () => {
      it('Should return tracked CommandeFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommandeFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
