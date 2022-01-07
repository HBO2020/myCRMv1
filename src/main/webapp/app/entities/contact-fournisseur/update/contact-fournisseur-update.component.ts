import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IContactFournisseur, ContactFournisseur } from '../contact-fournisseur.model';
import { ContactFournisseurService } from '../service/contact-fournisseur.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';

@Component({
  selector: 'jhi-contact-fournisseur-update',
  templateUrl: './contact-fournisseur-update.component.html',
})
export class ContactFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  fournisseursSharedCollection: IFournisseur[] = [];

  editForm = this.fb.group({
    id: [],
    contactFrName: [],
    contactfrPrenom: [],
    contactFrEmail: [],
    contactFrMobilePhone: [],
    contactFrStatus: [],
    fournisseur: [],
  });

  constructor(
    protected contactFournisseurService: ContactFournisseurService,
    protected fournisseurService: FournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactFournisseur }) => {
      this.updateForm(contactFournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactFournisseur = this.createFromForm();
    if (contactFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.contactFournisseurService.update(contactFournisseur));
    } else {
      this.subscribeToSaveResponse(this.contactFournisseurService.create(contactFournisseur));
    }
  }

  trackFournisseurById(index: number, item: IFournisseur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactFournisseur>>): void {
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

  protected updateForm(contactFournisseur: IContactFournisseur): void {
    this.editForm.patchValue({
      id: contactFournisseur.id,
      contactFrName: contactFournisseur.contactFrName,
      contactfrPrenom: contactFournisseur.contactfrPrenom,
      contactFrEmail: contactFournisseur.contactFrEmail,
      contactFrMobilePhone: contactFournisseur.contactFrMobilePhone,
      contactFrStatus: contactFournisseur.contactFrStatus,
      fournisseur: contactFournisseur.fournisseur,
    });

    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      contactFournisseur.fournisseur
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
  }

  protected createFromForm(): IContactFournisseur {
    return {
      ...new ContactFournisseur(),
      id: this.editForm.get(['id'])!.value,
      contactFrName: this.editForm.get(['contactFrName'])!.value,
      contactfrPrenom: this.editForm.get(['contactfrPrenom'])!.value,
      contactFrEmail: this.editForm.get(['contactFrEmail'])!.value,
      contactFrMobilePhone: this.editForm.get(['contactFrMobilePhone'])!.value,
      contactFrStatus: this.editForm.get(['contactFrStatus'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
    };
  }
}
