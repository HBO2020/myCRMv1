import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPayementClient, PayementClient } from '../payement-client.model';
import { PayementClientService } from '../service/payement-client.service';

@Component({
  selector: 'jhi-payement-client-update',
  templateUrl: './payement-client-update.component.html',
})
export class PayementClientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    payementClIdent: [],
    payementClDate: [],
    payementClMode: [],
    payementClEcheance: [],
    payementClMontant: [],
  });

  constructor(
    protected payementClientService: PayementClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payementClient }) => {
      this.updateForm(payementClient);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payementClient = this.createFromForm();
    if (payementClient.id !== undefined) {
      this.subscribeToSaveResponse(this.payementClientService.update(payementClient));
    } else {
      this.subscribeToSaveResponse(this.payementClientService.create(payementClient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayementClient>>): void {
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

  protected updateForm(payementClient: IPayementClient): void {
    this.editForm.patchValue({
      id: payementClient.id,
      payementClIdent: payementClient.payementClIdent,
      payementClDate: payementClient.payementClDate,
      payementClMode: payementClient.payementClMode,
      payementClEcheance: payementClient.payementClEcheance,
      payementClMontant: payementClient.payementClMontant,
    });
  }

  protected createFromForm(): IPayementClient {
    return {
      ...new PayementClient(),
      id: this.editForm.get(['id'])!.value,
      payementClIdent: this.editForm.get(['payementClIdent'])!.value,
      payementClDate: this.editForm.get(['payementClDate'])!.value,
      payementClMode: this.editForm.get(['payementClMode'])!.value,
      payementClEcheance: this.editForm.get(['payementClEcheance'])!.value,
      payementClMontant: this.editForm.get(['payementClMontant'])!.value,
    };
  }
}
