import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICiviliteClient, CiviliteClient } from '../civilite-client.model';
import { CiviliteClientService } from '../service/civilite-client.service';

@Component({
  selector: 'jhi-civilite-client-update',
  templateUrl: './civilite-client-update.component.html',
})
export class CiviliteClientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    civiliteLibelleCl: [],
    civiliteCodeCl: [],
  });

  constructor(
    protected civiliteClientService: CiviliteClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civiliteClient }) => {
      this.updateForm(civiliteClient);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const civiliteClient = this.createFromForm();
    if (civiliteClient.id !== undefined) {
      this.subscribeToSaveResponse(this.civiliteClientService.update(civiliteClient));
    } else {
      this.subscribeToSaveResponse(this.civiliteClientService.create(civiliteClient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiviliteClient>>): void {
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

  protected updateForm(civiliteClient: ICiviliteClient): void {
    this.editForm.patchValue({
      id: civiliteClient.id,
      civiliteLibelleCl: civiliteClient.civiliteLibelleCl,
      civiliteCodeCl: civiliteClient.civiliteCodeCl,
    });
  }

  protected createFromForm(): ICiviliteClient {
    return {
      ...new CiviliteClient(),
      id: this.editForm.get(['id'])!.value,
      civiliteLibelleCl: this.editForm.get(['civiliteLibelleCl'])!.value,
      civiliteCodeCl: this.editForm.get(['civiliteCodeCl'])!.value,
    };
  }
}
