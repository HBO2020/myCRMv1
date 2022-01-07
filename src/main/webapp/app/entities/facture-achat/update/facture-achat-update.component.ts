import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFactureAchat, FactureAchat } from '../facture-achat.model';
import { FactureAchatService } from '../service/facture-achat.service';
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

@Component({
  selector: 'jhi-facture-achat-update',
  templateUrl: './facture-achat-update.component.html',
})
export class FactureAchatUpdateComponent implements OnInit {
  isSaving = false;

  fournisseursSharedCollection: IFournisseur[] = [];
  payementFournisseursSharedCollection: IPayementFournisseur[] = [];
  livraisonFrsSharedCollection: ILivraisonFr[] = [];
  clientsSharedCollection: IClient[] = [];
  payementClientsSharedCollection: IPayementClient[] = [];

  editForm = this.fb.group({
    id: [],
    achatIdentFac: [],
    achatDateEffet: [],
    achatDateUpdate: [],
    achatStatusFact: [],
    achatMontantHT: [],
    achatMontantTVA: [],
    achatMontantTTC: [],
    fournisseur: [],
    payementFr: [],
    livraisonFr: [],
    client: [],
    payementCl: [],
  });

  constructor(
    protected factureAchatService: FactureAchatService,
    protected fournisseurService: FournisseurService,
    protected payementFournisseurService: PayementFournisseurService,
    protected livraisonFrService: LivraisonFrService,
    protected clientService: ClientService,
    protected payementClientService: PayementClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureAchat }) => {
      this.updateForm(factureAchat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factureAchat = this.createFromForm();
    if (factureAchat.id !== undefined) {
      this.subscribeToSaveResponse(this.factureAchatService.update(factureAchat));
    } else {
      this.subscribeToSaveResponse(this.factureAchatService.create(factureAchat));
    }
  }

  trackFournisseurById(index: number, item: IFournisseur): number {
    return item.id!;
  }

  trackPayementFournisseurById(index: number, item: IPayementFournisseur): number {
    return item.id!;
  }

  trackLivraisonFrById(index: number, item: ILivraisonFr): number {
    return item.id!;
  }

  trackClientById(index: number, item: IClient): number {
    return item.id!;
  }

  trackPayementClientById(index: number, item: IPayementClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactureAchat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(factureAchat: IFactureAchat): void {
    this.editForm.patchValue({
      id: factureAchat.id,
      achatIdentFac: factureAchat.achatIdentFac,
      achatDateEffet: factureAchat.achatDateEffet,
      achatDateUpdate: factureAchat.achatDateUpdate,
      achatStatusFact: factureAchat.achatStatusFact,
      achatMontantHT: factureAchat.achatMontantHT,
      achatMontantTVA: factureAchat.achatMontantTVA,
      achatMontantTTC: factureAchat.achatMontantTTC,
      fournisseur: factureAchat.fournisseur,
      payementFr: factureAchat.payementFr,
      livraisonFr: factureAchat.livraisonFr,
      client: factureAchat.client,
      payementCl: factureAchat.payementCl,
    });

    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      factureAchat.fournisseur
    );
    this.payementFournisseursSharedCollection = this.payementFournisseurService.addPayementFournisseurToCollectionIfMissing(
      this.payementFournisseursSharedCollection,
      factureAchat.payementFr
    );
    this.livraisonFrsSharedCollection = this.livraisonFrService.addLivraisonFrToCollectionIfMissing(
      this.livraisonFrsSharedCollection,
      factureAchat.livraisonFr
    );
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, factureAchat.client);
    this.payementClientsSharedCollection = this.payementClientService.addPayementClientToCollectionIfMissing(
      this.payementClientsSharedCollection,
      factureAchat.payementCl
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));

    this.payementFournisseurService
      .query()
      .pipe(map((res: HttpResponse<IPayementFournisseur[]>) => res.body ?? []))
      .pipe(
        map((payementFournisseurs: IPayementFournisseur[]) =>
          this.payementFournisseurService.addPayementFournisseurToCollectionIfMissing(
            payementFournisseurs,
            this.editForm.get('payementFr')!.value
          )
        )
      )
      .subscribe((payementFournisseurs: IPayementFournisseur[]) => (this.payementFournisseursSharedCollection = payementFournisseurs));

    this.livraisonFrService
      .query()
      .pipe(map((res: HttpResponse<ILivraisonFr[]>) => res.body ?? []))
      .pipe(
        map((livraisonFrs: ILivraisonFr[]) =>
          this.livraisonFrService.addLivraisonFrToCollectionIfMissing(livraisonFrs, this.editForm.get('livraisonFr')!.value)
        )
      )
      .subscribe((livraisonFrs: ILivraisonFr[]) => (this.livraisonFrsSharedCollection = livraisonFrs));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.payementClientService
      .query()
      .pipe(map((res: HttpResponse<IPayementClient[]>) => res.body ?? []))
      .pipe(
        map((payementClients: IPayementClient[]) =>
          this.payementClientService.addPayementClientToCollectionIfMissing(payementClients, this.editForm.get('payementCl')!.value)
        )
      )
      .subscribe((payementClients: IPayementClient[]) => (this.payementClientsSharedCollection = payementClients));
  }

  protected createFromForm(): IFactureAchat {
    return {
      ...new FactureAchat(),
      id: this.editForm.get(['id'])!.value,
      achatIdentFac: this.editForm.get(['achatIdentFac'])!.value,
      achatDateEffet: this.editForm.get(['achatDateEffet'])!.value,
      achatDateUpdate: this.editForm.get(['achatDateUpdate'])!.value,
      achatStatusFact: this.editForm.get(['achatStatusFact'])!.value,
      achatMontantHT: this.editForm.get(['achatMontantHT'])!.value,
      achatMontantTVA: this.editForm.get(['achatMontantTVA'])!.value,
      achatMontantTTC: this.editForm.get(['achatMontantTTC'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
      payementFr: this.editForm.get(['payementFr'])!.value,
      livraisonFr: this.editForm.get(['livraisonFr'])!.value,
      client: this.editForm.get(['client'])!.value,
      payementCl: this.editForm.get(['payementCl'])!.value,
    };
  }
}
