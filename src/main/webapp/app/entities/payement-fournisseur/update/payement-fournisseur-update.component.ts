import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPayementFournisseur, PayementFournisseur } from '../payement-fournisseur.model';
import { PayementFournisseurService } from '../service/payement-fournisseur.service';

@Component({
  selector: 'jhi-payement-fournisseur-update',
  templateUrl: './payement-fournisseur-update.component.html',
})
export class PayementFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    payementFrIdent: [],
    payementFrDate: [],
    payementFrMode: [],
    payementFrEcheance: [],
    payementFrMontant: [],
  });

  constructor(
    protected payementFournisseurService: PayementFournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payementFournisseur }) => {
      this.updateForm(payementFournisseur);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payementFournisseur = this.createFromForm();
    if (payementFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.payementFournisseurService.update(payementFournisseur));
    } else {
      this.subscribeToSaveResponse(this.payementFournisseurService.create(payementFournisseur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayementFournisseur>>): void {
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

  protected updateForm(payementFournisseur: IPayementFournisseur): void {
    this.editForm.patchValue({
      id: payementFournisseur.id,
      payementFrIdent: payementFournisseur.payementFrIdent,
      payementFrDate: payementFournisseur.payementFrDate,
      payementFrMode: payementFournisseur.payementFrMode,
      payementFrEcheance: payementFournisseur.payementFrEcheance,
      payementFrMontant: payementFournisseur.payementFrMontant,
    });
  }

  protected createFromForm(): IPayementFournisseur {
    return {
      ...new PayementFournisseur(),
      id: this.editForm.get(['id'])!.value,
      payementFrIdent: this.editForm.get(['payementFrIdent'])!.value,
      payementFrDate: this.editForm.get(['payementFrDate'])!.value,
      payementFrMode: this.editForm.get(['payementFrMode'])!.value,
      payementFrEcheance: this.editForm.get(['payementFrEcheance'])!.value,
      payementFrMontant: this.editForm.get(['payementFrMontant'])!.value,
    };
  }
}
