import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICiviliteFournisseur, CiviliteFournisseur } from '../civilite-fournisseur.model';
import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';

@Component({
  selector: 'jhi-civilite-fournisseur-update',
  templateUrl: './civilite-fournisseur-update.component.html',
})
export class CiviliteFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    civiliteFrLibelle: [],
    civiliteFrCode: [],
  });

  constructor(
    protected civiliteFournisseurService: CiviliteFournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civiliteFournisseur }) => {
      this.updateForm(civiliteFournisseur);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const civiliteFournisseur = this.createFromForm();
    if (civiliteFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.civiliteFournisseurService.update(civiliteFournisseur));
    } else {
      this.subscribeToSaveResponse(this.civiliteFournisseurService.create(civiliteFournisseur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiviliteFournisseur>>): void {
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

  protected updateForm(civiliteFournisseur: ICiviliteFournisseur): void {
    this.editForm.patchValue({
      id: civiliteFournisseur.id,
      civiliteFrLibelle: civiliteFournisseur.civiliteFrLibelle,
      civiliteFrCode: civiliteFournisseur.civiliteFrCode,
    });
  }

  protected createFromForm(): ICiviliteFournisseur {
    return {
      ...new CiviliteFournisseur(),
      id: this.editForm.get(['id'])!.value,
      civiliteFrLibelle: this.editForm.get(['civiliteFrLibelle'])!.value,
      civiliteFrCode: this.editForm.get(['civiliteFrCode'])!.value,
    };
  }
}
