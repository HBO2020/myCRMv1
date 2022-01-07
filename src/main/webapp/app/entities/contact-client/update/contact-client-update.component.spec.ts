import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactClientService } from '../service/contact-client.service';
import { IContactClient, ContactClient } from '../contact-client.model';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

import { ContactClientUpdateComponent } from './contact-client-update.component';

describe('ContactClient Management Update Component', () => {
  let comp: ContactClientUpdateComponent;
  let fixture: ComponentFixture<ContactClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactClientService: ContactClientService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactClientUpdateComponent],
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
      .overrideTemplate(ContactClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactClientService = TestBed.inject(ContactClientService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const contactClient: IContactClient = { id: 456 };
      const client: IClient = { id: 35190 };
      contactClient.client = client;

      const clientCollection: IClient[] = [{ id: 60051 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contactClient });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contactClient: IContactClient = { id: 456 };
      const client: IClient = { id: 12673 };
      contactClient.client = client;

      activatedRoute.data = of({ contactClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contactClient));
      expect(comp.clientsSharedCollection).toContain(client);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactClient>>();
      const contactClient = { id: 123 };
      jest.spyOn(contactClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactClientService.update).toHaveBeenCalledWith(contactClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactClient>>();
      const contactClient = new ContactClient();
      jest.spyOn(contactClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactClient }));
      saveSubject.complete();

      // THEN
      expect(contactClientService.create).toHaveBeenCalledWith(contactClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactClient>>();
      const contactClient = { id: 123 };
      jest.spyOn(contactClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactClientService.update).toHaveBeenCalledWith(contactClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClientById', () => {
      it('Should return tracked Client primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
