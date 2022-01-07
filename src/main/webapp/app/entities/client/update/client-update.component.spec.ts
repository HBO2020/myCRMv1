import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientService } from '../service/client.service';
import { IClient, Client } from '../client.model';
import { ICiviliteClient } from 'app/entities/civilite-client/civilite-client.model';
import { CiviliteClientService } from 'app/entities/civilite-client/service/civilite-client.service';

import { ClientUpdateComponent } from './client-update.component';

describe('Client Management Update Component', () => {
  let comp: ClientUpdateComponent;
  let fixture: ComponentFixture<ClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientService: ClientService;
  let civiliteClientService: CiviliteClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClientUpdateComponent],
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
      .overrideTemplate(ClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientService = TestBed.inject(ClientService);
    civiliteClientService = TestBed.inject(CiviliteClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CiviliteClient query and add missing value', () => {
      const client: IClient = { id: 456 };
      const civilitecl: ICiviliteClient = { id: 49994 };
      client.civilitecl = civilitecl;

      const civiliteClientCollection: ICiviliteClient[] = [{ id: 18264 }];
      jest.spyOn(civiliteClientService, 'query').mockReturnValue(of(new HttpResponse({ body: civiliteClientCollection })));
      const additionalCiviliteClients = [civilitecl];
      const expectedCollection: ICiviliteClient[] = [...additionalCiviliteClients, ...civiliteClientCollection];
      jest.spyOn(civiliteClientService, 'addCiviliteClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(civiliteClientService.query).toHaveBeenCalled();
      expect(civiliteClientService.addCiviliteClientToCollectionIfMissing).toHaveBeenCalledWith(
        civiliteClientCollection,
        ...additionalCiviliteClients
      );
      expect(comp.civiliteClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const client: IClient = { id: 456 };
      const civilitecl: ICiviliteClient = { id: 10934 };
      client.civilitecl = civilitecl;

      activatedRoute.data = of({ client });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(client));
      expect(comp.civiliteClientsSharedCollection).toContain(civilitecl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Client>>();
      const client = { id: 123 };
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientService.update).toHaveBeenCalledWith(client);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Client>>();
      const client = new Client();
      jest.spyOn(clientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: client }));
      saveSubject.complete();

      // THEN
      expect(clientService.create).toHaveBeenCalledWith(client);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Client>>();
      const client = { id: 123 };
      jest.spyOn(clientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ client });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientService.update).toHaveBeenCalledWith(client);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCiviliteClientById', () => {
      it('Should return tracked CiviliteClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCiviliteClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
