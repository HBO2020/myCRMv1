import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICommandeClient, CommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';
import { LivraisonClService } from 'app/entities/livraison-cl/service/livraison-cl.service';

@Component({
  selector: 'jhi-commande-client-update',
  templateUrl: './commande-client-update.component.html',
})
export class CommandeClientUpdateComponent implements OnInit {
  isSaving = false;

  clientsSharedCollection: IClient[] = [];
  livraisonClsSharedCollection: ILivraisonCl[] = [];

  editForm = this.fb.group({
    id: [],
    cmdIdenCl: [],
    cmdDateEffetCl: [],
    cmdDateLivraisonCl: [],
    client: [],
    livraisonCl: [],
  });

  constructor(
    protected commandeClientService: CommandeClientService,
    protected clientService: ClientService,
    protected livraisonClService: LivraisonClService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeClient }) => {
      this.updateForm(commandeClient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeClient = this.createFromForm();
    if (commandeClient.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeClientService.update(commandeClient));
    } else {
      this.subscribeToSaveResponse(this.commandeClientService.create(commandeClient));
    }
  }

  trackClientById(index: number, item: IClient): number {
    return item.id!;
  }

  trackLivraisonClById(index: number, item: ILivraisonCl): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeClient>>): void {
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

  protected updateForm(commandeClient: ICommandeClient): void {
    this.editForm.patchValue({
      id: commandeClient.id,
      cmdIdenCl: commandeClient.cmdIdenCl,
      cmdDateEffetCl: commandeClient.cmdDateEffetCl,
      cmdDateLivraisonCl: commandeClient.cmdDateLivraisonCl,
      client: commandeClient.client,
      livraisonCl: commandeClient.livraisonCl,
    });

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, commandeClient.client);
    this.livraisonClsSharedCollection = this.livraisonClService.addLivraisonClToCollectionIfMissing(
      this.livraisonClsSharedCollection,
      commandeClient.livraisonCl
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.livraisonClService
      .query()
      .pipe(map((res: HttpResponse<ILivraisonCl[]>) => res.body ?? []))
      .pipe(
        map((livraisonCls: ILivraisonCl[]) =>
          this.livraisonClService.addLivraisonClToCollectionIfMissing(livraisonCls, this.editForm.get('livraisonCl')!.value)
        )
      )
      .subscribe((livraisonCls: ILivraisonCl[]) => (this.livraisonClsSharedCollection = livraisonCls));
  }

  protected createFromForm(): ICommandeClient {
    return {
      ...new CommandeClient(),
      id: this.editForm.get(['id'])!.value,
      cmdIdenCl: this.editForm.get(['cmdIdenCl'])!.value,
      cmdDateEffetCl: this.editForm.get(['cmdDateEffetCl'])!.value,
      cmdDateLivraisonCl: this.editForm.get(['cmdDateLivraisonCl'])!.value,
      client: this.editForm.get(['client'])!.value,
      livraisonCl: this.editForm.get(['livraisonCl'])!.value,
    };
  }
}
