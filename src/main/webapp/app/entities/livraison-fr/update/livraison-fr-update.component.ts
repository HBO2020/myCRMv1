import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILivraisonFr, LivraisonFr } from '../livraison-fr.model';
import { LivraisonFrService } from '../service/livraison-fr.service';

@Component({
  selector: 'jhi-livraison-fr-update',
  templateUrl: './livraison-fr-update.component.html',
})
export class LivraisonFrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    bonLivIdent: [],
    livFrDate: [],
    livFrDateUpdate: [],
    livDateEffet: [],
    bonLivTotal: [],
  });

  constructor(protected livraisonFrService: LivraisonFrService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonFr }) => {
      this.updateForm(livraisonFr);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livraisonFr = this.createFromForm();
    if (livraisonFr.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonFrService.update(livraisonFr));
    } else {
      this.subscribeToSaveResponse(this.livraisonFrService.create(livraisonFr));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraisonFr>>): void {
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

  protected updateForm(livraisonFr: ILivraisonFr): void {
    this.editForm.patchValue({
      id: livraisonFr.id,
      bonLivIdent: livraisonFr.bonLivIdent,
      livFrDate: livraisonFr.livFrDate,
      livFrDateUpdate: livraisonFr.livFrDateUpdate,
      livDateEffet: livraisonFr.livDateEffet,
      bonLivTotal: livraisonFr.bonLivTotal,
    });
  }

  protected createFromForm(): ILivraisonFr {
    return {
      ...new LivraisonFr(),
      id: this.editForm.get(['id'])!.value,
      bonLivIdent: this.editForm.get(['bonLivIdent'])!.value,
      livFrDate: this.editForm.get(['livFrDate'])!.value,
      livFrDateUpdate: this.editForm.get(['livFrDateUpdate'])!.value,
      livDateEffet: this.editForm.get(['livDateEffet'])!.value,
      bonLivTotal: this.editForm.get(['bonLivTotal'])!.value,
    };
  }
}
