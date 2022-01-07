import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LigneCmdClientService } from '../service/ligne-cmd-client.service';
import { ILigneCmdClient, LigneCmdClient } from '../ligne-cmd-client.model';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { CommandeClientService } from 'app/entities/commande-client/service/commande-client.service';

import { LigneCmdClientUpdateComponent } from './ligne-cmd-client-update.component';

describe('LigneCmdClient Management Update Component', () => {
  let comp: LigneCmdClientUpdateComponent;
  let fixture: ComponentFixture<LigneCmdClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ligneCmdClientService: LigneCmdClientService;
  let commandeClientService: CommandeClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LigneCmdClientUpdateComponent],
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
      .overrideTemplate(LigneCmdClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneCmdClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ligneCmdClientService = TestBed.inject(LigneCmdClientService);
    commandeClientService = TestBed.inject(CommandeClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CommandeClient query and add missing value', () => {
      const ligneCmdClient: ILigneCmdClient = { id: 456 };
      const commandeClient: ICommandeClient = { id: 5129 };
      ligneCmdClient.commandeClient = commandeClient;

      const commandeClientCollection: ICommandeClient[] = [{ id: 58961 }];
      jest.spyOn(commandeClientService, 'query').mockReturnValue(of(new HttpResponse({ body: commandeClientCollection })));
      const additionalCommandeClients = [commandeClient];
      const expectedCollection: ICommandeClient[] = [...additionalCommandeClients, ...commandeClientCollection];
      jest.spyOn(commandeClientService, 'addCommandeClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneCmdClient });
      comp.ngOnInit();

      expect(commandeClientService.query).toHaveBeenCalled();
      expect(commandeClientService.addCommandeClientToCollectionIfMissing).toHaveBeenCalledWith(
        commandeClientCollection,
        ...additionalCommandeClients
      );
      expect(comp.commandeClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ligneCmdClient: ILigneCmdClient = { id: 456 };
      const commandeClient: ICommandeClient = { id: 96012 };
      ligneCmdClient.commandeClient = commandeClient;

      activatedRoute.data = of({ ligneCmdClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ligneCmdClient));
      expect(comp.commandeClientsSharedCollection).toContain(commandeClient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdClient>>();
      const ligneCmdClient = { id: 123 };
      jest.spyOn(ligneCmdClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneCmdClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ligneCmdClientService.update).toHaveBeenCalledWith(ligneCmdClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdClient>>();
      const ligneCmdClient = new LigneCmdClient();
      jest.spyOn(ligneCmdClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneCmdClient }));
      saveSubject.complete();

      // THEN
      expect(ligneCmdClientService.create).toHaveBeenCalledWith(ligneCmdClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LigneCmdClient>>();
      const ligneCmdClient = { id: 123 };
      jest.spyOn(ligneCmdClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneCmdClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ligneCmdClientService.update).toHaveBeenCalledWith(ligneCmdClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCommandeClientById', () => {
      it('Should return tracked CommandeClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommandeClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
