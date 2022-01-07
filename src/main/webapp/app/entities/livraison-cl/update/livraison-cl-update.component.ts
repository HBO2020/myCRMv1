import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILivraisonCl, LivraisonCl } from '../livraison-cl.model';
import { LivraisonClService } from '../service/livraison-cl.service';

@Component({
  selector: 'jhi-livraison-cl-update',
  templateUrl: './livraison-cl-update.component.html',
})
export class LivraisonClUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    bonLivIdentCl: [],
    livDateCl: [],
    livDateUpdateCl: [],
    livDateEffetCl: [],
    bonLivTotalCl: [],
  });

  constructor(protected livraisonClService: LivraisonClService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonCl }) => {
      this.updateForm(livraisonCl);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livraisonCl = this.createFromForm();
    if (livraisonCl.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonClService.update(livraisonCl));
    } else {
      this.subscribeToSaveResponse(this.livraisonClService.create(livraisonCl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraisonCl>>): void {
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

  protected updateForm(livraisonCl: ILivraisonCl): void {
    this.editForm.patchValue({
      id: livraisonCl.id,
      bonLivIdentCl: livraisonCl.bonLivIdentCl,
      livDateCl: livraisonCl.livDateCl,
      livDateUpdateCl: livraisonCl.livDateUpdateCl,
      livDateEffetCl: livraisonCl.livDateEffetCl,
      bonLivTotalCl: livraisonCl.bonLivTotalCl,
    });
  }

  protected createFromForm(): ILivraisonCl {
    return {
      ...new LivraisonCl(),
      id: this.editForm.get(['id'])!.value,
      bonLivIdentCl: this.editForm.get(['bonLivIdentCl'])!.value,
      livDateCl: this.editForm.get(['livDateCl'])!.value,
      livDateUpdateCl: this.editForm.get(['livDateUpdateCl'])!.value,
      livDateEffetCl: this.editForm.get(['livDateEffetCl'])!.value,
      bonLivTotalCl: this.editForm.get(['bonLivTotalCl'])!.value,
    };
  }
}
