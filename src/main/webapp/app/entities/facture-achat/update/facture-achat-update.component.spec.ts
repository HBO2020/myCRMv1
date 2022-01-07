import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FactureAchatService } from '../service/facture-achat.service';
import { IFactureAchat, FactureAchat } from '../facture-achat.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IPayementFournisseur } from 'app/entities/payement-fournisseur/payement-fournisseur.model';
import { PayementFournisseurService } from 'app/entities/payement-fournisseur/service/payement-fournisseur.service';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';
import { LivraisonFrService } from 'app/entities/livraison-fr/service/livraison-fr.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IPayementClient } from 'app/entities/payement-client/payement-client.model';
import { PayementClientService } from 'app/entities/payement-client/service/payement-client.service';

import { FactureAchatUpdateComponent } from './facture-achat-update.component';

describe('FactureAchat Management Update Component', () => {
  let comp: FactureAchatUpdateComponent;
  let fixture: ComponentFixture<FactureAchatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factureAchatService: FactureAchatService;
  let fournisseurService: FournisseurService;
  let payementFournisseurService: PayementFournisseurService;
  let livraisonFrService: LivraisonFrService;
  let clientService: ClientService;
  let payementClientService: PayementClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FactureAchatUpdateComponent],
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
      .overrideTemplate(FactureAchatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureAchatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factureAchatService = TestBed.inject(FactureAchatService);
    fournisseurService = TestBed.inject(FournisseurService);
    payementFournisseurService = TestBed.inject(PayementFournisseurService);
    livraisonFrService = TestBed.inject(LivraisonFrService);
    clientService = TestBed.inject(ClientService);
    payementClientService = TestBed.inject(PayementClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Fournisseur query and add missing value', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const fournisseur: IFournisseur = { id: 18470 };
      factureAchat.fournisseur = fournisseur;

      const fournisseurCollection: IFournisseur[] = [{ id: 62101 }];
      jest.spyOn(fournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: fournisseurCollection })));
      const additionalFournisseurs = [fournisseur];
      const expectedCollection: IFournisseur[] = [...additionalFournisseurs, ...fournisseurCollection];
      jest.spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(fournisseurService.query).toHaveBeenCalled();
      expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(fournisseurCollection, ...additionalFournisseurs);
      expect(comp.fournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PayementFournisseur query and add missing value', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const payementFr: IPayementFournisseur = { id: 47001 };
      factureAchat.payementFr = payementFr;

      const payementFournisseurCollection: IPayementFournisseur[] = [{ id: 97296 }];
      jest.spyOn(payementFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: payementFournisseurCollection })));
      const additionalPayementFournisseurs = [payementFr];
      const expectedCollection: IPayementFournisseur[] = [...additionalPayementFournisseurs, ...payementFournisseurCollection];
      jest.spyOn(payementFournisseurService, 'addPayementFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(payementFournisseurService.query).toHaveBeenCalled();
      expect(payementFournisseurService.addPayementFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        payementFournisseurCollection,
        ...additionalPayementFournisseurs
      );
      expect(comp.payementFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LivraisonFr query and add missing value', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const livraisonFr: ILivraisonFr = { id: 22536 };
      factureAchat.livraisonFr = livraisonFr;

      const livraisonFrCollection: ILivraisonFr[] = [{ id: 32234 }];
      jest.spyOn(livraisonFrService, 'query').mockReturnValue(of(new HttpResponse({ body: livraisonFrCollection })));
      const additionalLivraisonFrs = [livraisonFr];
      const expectedCollection: ILivraisonFr[] = [...additionalLivraisonFrs, ...livraisonFrCollection];
      jest.spyOn(livraisonFrService, 'addLivraisonFrToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(livraisonFrService.query).toHaveBeenCalled();
      expect(livraisonFrService.addLivraisonFrToCollectionIfMissing).toHaveBeenCalledWith(livraisonFrCollection, ...additionalLivraisonFrs);
      expect(comp.livraisonFrsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const client: IClient = { id: 78038 };
      factureAchat.client = client;

      const clientCollection: IClient[] = [{ id: 99004 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PayementClient query and add missing value', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const payementCl: IPayementClient = { id: 9416 };
      factureAchat.payementCl = payementCl;

      const payementClientCollection: IPayementClient[] = [{ id: 31517 }];
      jest.spyOn(payementClientService, 'query').mockReturnValue(of(new HttpResponse({ body: payementClientCollection })));
      const additionalPayementClients = [payementCl];
      const expectedCollection: IPayementClient[] = [...additionalPayementClients, ...payementClientCollection];
      jest.spyOn(payementClientService, 'addPayementClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(payementClientService.query).toHaveBeenCalled();
      expect(payementClientService.addPayementClientToCollectionIfMissing).toHaveBeenCalledWith(
        payementClientCollection,
        ...additionalPayementClients
      );
      expect(comp.payementClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const factureAchat: IFactureAchat = { id: 456 };
      const fournisseur: IFournisseur = { id: 10604 };
      factureAchat.fournisseur = fournisseur;
      const payementFr: IPayementFournisseur = { id: 26461 };
      factureAchat.payementFr = payementFr;
      const livraisonFr: ILivraisonFr = { id: 93213 };
      factureAchat.livraisonFr = livraisonFr;
      const client: IClient = { id: 44840 };
      factureAchat.client = client;
      const payementCl: IPayementClient = { id: 40761 };
      factureAchat.payementCl = payementCl;

      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(factureAchat));
      expect(comp.fournisseursSharedCollection).toContain(fournisseur);
      expect(comp.payementFournisseursSharedCollection).toContain(payementFr);
      expect(comp.livraisonFrsSharedCollection).toContain(livraisonFr);
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.payementClientsSharedCollection).toContain(payementCl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureAchat>>();
      const factureAchat = { id: 123 };
      jest.spyOn(factureAchatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureAchat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(factureAchatService.update).toHaveBeenCalledWith(factureAchat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureAchat>>();
      const factureAchat = new FactureAchat();
      jest.spyOn(factureAchatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureAchat }));
      saveSubject.complete();

      // THEN
      expect(factureAchatService.create).toHaveBeenCalledWith(factureAchat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FactureAchat>>();
      const factureAchat = { id: 123 };
      jest.spyOn(factureAchatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureAchat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factureAchatService.update).toHaveBeenCalledWith(factureAchat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFournisseurById', () => {
      it('Should return tracked Fournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPayementFournisseurById', () => {
      it('Should return tracked PayementFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPayementFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLivraisonFrById', () => {
      it('Should return tracked LivraisonFr primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLivraisonFrById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackClientById', () => {
      it('Should return tracked Client primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPayementClientById', () => {
      it('Should return tracked PayementClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPayementClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
