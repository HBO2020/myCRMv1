import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILigneCmdClient, LigneCmdClient } from '../ligne-cmd-client.model';
import { LigneCmdClientService } from '../service/ligne-cmd-client.service';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { CommandeClientService } from 'app/entities/commande-client/service/commande-client.service';

@Component({
  selector: 'jhi-ligne-cmd-client-update',
  templateUrl: './ligne-cmd-client-update.component.html',
})
export class LigneCmdClientUpdateComponent implements OnInit {
  isSaving = false;

  commandeClientsSharedCollection: ICommandeClient[] = [];

  editForm = this.fb.group({
    id: [],
    cmdQnCl: [],
    cmdNmPiecesCl: [],
    commandeClient: [],
  });

  constructor(
    protected ligneCmdClientService: LigneCmdClientService,
    protected commandeClientService: CommandeClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCmdClient }) => {
      this.updateForm(ligneCmdClient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneCmdClient = this.createFromForm();
    if (ligneCmdClient.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneCmdClientService.update(ligneCmdClient));
    } else {
      this.subscribeToSaveResponse(this.ligneCmdClientService.create(ligneCmdClient));
    }
  }

  trackCommandeClientById(index: number, item: ICommandeClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneCmdClient>>): void {
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

  protected updateForm(ligneCmdClient: ILigneCmdClient): void {
    this.editForm.patchValue({
      id: ligneCmdClient.id,
      cmdQnCl: ligneCmdClient.cmdQnCl,
      cmdNmPiecesCl: ligneCmdClient.cmdNmPiecesCl,
      commandeClient: ligneCmdClient.commandeClient,
    });

    this.commandeClientsSharedCollection = this.commandeClientService.addCommandeClientToCollectionIfMissing(
      this.commandeClientsSharedCollection,
      ligneCmdClient.commandeClient
    );
  }

  protected loadRelationshipsOptions(): void {
    this.commandeClientService
      .query()
      .pipe(map((res: HttpResponse<ICommandeClient[]>) => res.body ?? []))
      .pipe(
        map((commandeClients: ICommandeClient[]) =>
          this.commandeClientService.addCommandeClientToCollectionIfMissing(commandeClients, this.editForm.get('commandeClient')!.value)
        )
      )
      .subscribe((commandeClients: ICommandeClient[]) => (this.commandeClientsSharedCollection = commandeClients));
  }

  protected createFromForm(): ILigneCmdClient {
    return {
      ...new LigneCmdClient(),
      id: this.editForm.get(['id'])!.value,
      cmdQnCl: this.editForm.get(['cmdQnCl'])!.value,
      cmdNmPiecesCl: this.editForm.get(['cmdNmPiecesCl'])!.value,
      commandeClient: this.editForm.get(['commandeClient'])!.value,
    };
  }
}
