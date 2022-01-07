import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILigneLivClient, LigneLivClient } from '../ligne-liv-client.model';
import { LigneLivClientService } from '../service/ligne-liv-client.service';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';
import { LivraisonClService } from 'app/entities/livraison-cl/service/livraison-cl.service';

@Component({
  selector: 'jhi-ligne-liv-client-update',
  templateUrl: './ligne-liv-client-update.component.html',
})
export class LigneLivClientUpdateComponent implements OnInit {
  isSaving = false;

  livraisonClsSharedCollection: ILivraisonCl[] = [];

  editForm = this.fb.group({
    id: [],
    livQuantiteCl: [],
    livNmPiecesCl: [],
    livTotalPrixCl: [],
    livraisonCl: [],
  });

  constructor(
    protected ligneLivClientService: LigneLivClientService,
    protected livraisonClService: LivraisonClService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivClient }) => {
      this.updateForm(ligneLivClient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneLivClient = this.createFromForm();
    if (ligneLivClient.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneLivClientService.update(ligneLivClient));
    } else {
      this.subscribeToSaveResponse(this.ligneLivClientService.create(ligneLivClient));
    }
  }

  trackLivraisonClById(index: number, item: ILivraisonCl): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneLivClient>>): void {
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

  protected updateForm(ligneLivClient: ILigneLivClient): void {
    this.editForm.patchValue({
      id: ligneLivClient.id,
      livQuantiteCl: ligneLivClient.livQuantiteCl,
      livNmPiecesCl: ligneLivClient.livNmPiecesCl,
      livTotalPrixCl: ligneLivClient.livTotalPrixCl,
      livraisonCl: ligneLivClient.livraisonCl,
    });

    this.livraisonClsSharedCollection = this.livraisonClService.addLivraisonClToCollectionIfMissing(
      this.livraisonClsSharedCollection,
      ligneLivClient.livraisonCl
    );
  }

  protected loadRelationshipsOptions(): void {
    this.livraisonClService
      .query()
      .pipe(map((res: HttpResponse<ILivraisonCl[]>) => res.body ?? []))
      .pipe(
        map((livraisonCls: ILivraisonCl[]) =>
          this.livraisonClService.addLivraisonClToCollectionIfMissing(livraisonCls, this.editForm.get('livraisonCl')!.value)
        )
      )
      .subscribe((livraisonCls: ILivraisonCl[]) => (this.livraisonClsSharedCollection = livraisonCls));
  }

  protected createFromForm(): ILigneLivClient {
    return {
      ...new LigneLivClient(),
      id: this.editForm.get(['id'])!.value,
      livQuantiteCl: this.editForm.get(['livQuantiteCl'])!.value,
      livNmPiecesCl: this.editForm.get(['livNmPiecesCl'])!.value,
      livTotalPrixCl: this.editForm.get(['livTotalPrixCl'])!.value,
      livraisonCl: this.editForm.get(['livraisonCl'])!.value,
    };
  }
}
