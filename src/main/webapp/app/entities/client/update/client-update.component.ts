import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IClient, Client } from '../client.model';
import { ClientService } from '../service/client.service';
import { ICiviliteClient } from 'app/entities/civilite-client/civilite-client.model';
import { CiviliteClientService } from 'app/entities/civilite-client/service/civilite-client.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;

  civiliteClientsSharedCollection: ICiviliteClient[] = [];

  editForm = this.fb.group({
    id: [],
    clIdent: [],
    clRaisonSocial: [],
    clAdresse: [],
    clCodePostal: [],
    clVille: [],
    clCountry: [],
    clEmail: [],
    clNumeroMobile: [],
    clNumeroFax: [],
    clNumeroFixe: [],
    clDateCreation: [],
    clDateUpdate: [],
    clStatus: [],
    clNumeroSiret: [],
    civilitecl: [],
  });

  constructor(
    protected clientService: ClientService,
    protected civiliteClientService: CiviliteClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  trackCiviliteClientById(index: number, item: ICiviliteClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  protected updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      clIdent: client.clIdent,
      clRaisonSocial: client.clRaisonSocial,
      clAdresse: client.clAdresse,
      clCodePostal: client.clCodePostal,
      clVille: client.clVille,
      clCountry: client.clCountry,
      clEmail: client.clEmail,
      clNumeroMobile: client.clNumeroMobile,
      clNumeroFax: client.clNumeroFax,
      clNumeroFixe: client.clNumeroFixe,
      clDateCreation: client.clDateCreation,
      clDateUpdate: client.clDateUpdate,
      clStatus: client.clStatus,
      clNumeroSiret: client.clNumeroSiret,
      civilitecl: client.civilitecl,
    });

    this.civiliteClientsSharedCollection = this.civiliteClientService.addCiviliteClientToCollectionIfMissing(
      this.civiliteClientsSharedCollection,
      client.civilitecl
    );
  }

  protected loadRelationshipsOptions(): void {
    this.civiliteClientService
      .query()
      .pipe(map((res: HttpResponse<ICiviliteClient[]>) => res.body ?? []))
      .pipe(
        map((civiliteClients: ICiviliteClient[]) =>
          this.civiliteClientService.addCiviliteClientToCollectionIfMissing(civiliteClients, this.editForm.get('civilitecl')!.value)
        )
      )
      .subscribe((civiliteClients: ICiviliteClient[]) => (this.civiliteClientsSharedCollection = civiliteClients));
  }

  protected createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      clIdent: this.editForm.get(['clIdent'])!.value,
      clRaisonSocial: this.editForm.get(['clRaisonSocial'])!.value,
      clAdresse: this.editForm.get(['clAdresse'])!.value,
      clCodePostal: this.editForm.get(['clCodePostal'])!.value,
      clVille: this.editForm.get(['clVille'])!.value,
      clCountry: this.editForm.get(['clCountry'])!.value,
      clEmail: this.editForm.get(['clEmail'])!.value,
      clNumeroMobile: this.editForm.get(['clNumeroMobile'])!.value,
      clNumeroFax: this.editForm.get(['clNumeroFax'])!.value,
      clNumeroFixe: this.editForm.get(['clNumeroFixe'])!.value,
      clDateCreation: this.editForm.get(['clDateCreation'])!.value,
      clDateUpdate: this.editForm.get(['clDateUpdate'])!.value,
      clStatus: this.editForm.get(['clStatus'])!.value,
      clNumeroSiret: this.editForm.get(['clNumeroSiret'])!.value,
      civilitecl: this.editForm.get(['civilitecl'])!.value,
    };
  }
}
