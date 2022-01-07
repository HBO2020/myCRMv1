import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IContactClient, ContactClient } from '../contact-client.model';
import { ContactClientService } from '../service/contact-client.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

@Component({
  selector: 'jhi-contact-client-update',
  templateUrl: './contact-client-update.component.html',
})
export class ContactClientUpdateComponent implements OnInit {
  isSaving = false;

  clientsSharedCollection: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    contactNameCl: [],
    contactPrenomCl: [],
    contactEmailCl: [],
    contactMobilePhoneCl: [],
    contactStatusCl: [],
    client: [],
  });

  constructor(
    protected contactClientService: ContactClientService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactClient }) => {
      this.updateForm(contactClient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactClient = this.createFromForm();
    if (contactClient.id !== undefined) {
      this.subscribeToSaveResponse(this.contactClientService.update(contactClient));
    } else {
      this.subscribeToSaveResponse(this.contactClientService.create(contactClient));
    }
  }

  trackClientById(index: number, item: IClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactClient>>): void {
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

  protected updateForm(contactClient: IContactClient): void {
    this.editForm.patchValue({
      id: contactClient.id,
      contactNameCl: contactClient.contactNameCl,
      contactPrenomCl: contactClient.contactPrenomCl,
      contactEmailCl: contactClient.contactEmailCl,
      contactMobilePhoneCl: contactClient.contactMobilePhoneCl,
      contactStatusCl: contactClient.contactStatusCl,
      client: contactClient.client,
    });

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, contactClient.client);
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }

  protected createFromForm(): IContactClient {
    return {
      ...new ContactClient(),
      id: this.editForm.get(['id'])!.value,
      contactNameCl: this.editForm.get(['contactNameCl'])!.value,
      contactPrenomCl: this.editForm.get(['contactPrenomCl'])!.value,
      contactEmailCl: this.editForm.get(['contactEmailCl'])!.value,
      contactMobilePhoneCl: this.editForm.get(['contactMobilePhoneCl'])!.value,
      contactStatusCl: this.editForm.get(['contactStatusCl'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }
}
