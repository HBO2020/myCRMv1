import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICarts, Carts } from '../carts.model';
import { CartsService } from '../service/carts.service';

@Component({
  selector: 'jhi-carts-update',
  templateUrl: './carts-update.component.html',
})
export class CartsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cartIsEmpty: [],
    cartUserEmail: [],
    cartListProduct: [],
  });

  constructor(protected cartsService: CartsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carts }) => {
      this.updateForm(carts);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carts = this.createFromForm();
    if (carts.id !== undefined) {
      this.subscribeToSaveResponse(this.cartsService.update(carts));
    } else {
      this.subscribeToSaveResponse(this.cartsService.create(carts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarts>>): void {
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

  protected updateForm(carts: ICarts): void {
    this.editForm.patchValue({
      id: carts.id,
      cartIsEmpty: carts.cartIsEmpty,
      cartUserEmail: carts.cartUserEmail,
      cartListProduct: carts.cartListProduct,
    });
  }

  protected createFromForm(): ICarts {
    return {
      ...new Carts(),
      id: this.editForm.get(['id'])!.value,
      cartIsEmpty: this.editForm.get(['cartIsEmpty'])!.value,
      cartUserEmail: this.editForm.get(['cartUserEmail'])!.value,
      cartListProduct: this.editForm.get(['cartListProduct'])!.value,
    };
  }
}
