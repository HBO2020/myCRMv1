import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFactureVente, FactureVente } from '../facture-vente.model';
import { FactureVenteService } from '../service/facture-vente.service';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';
import { LivraisonClService } from 'app/entities/livraison-cl/service/livraison-cl.service';

@Component({
  selector: 'jhi-facture-vente-update',
  templateUrl: './facture-vente-update.component.html',
})
export class FactureVenteUpdateComponent implements OnInit {
  isSaving = false;

  livraisonClsSharedCollection: ILivraisonCl[] = [];

  editForm = this.fb.group({
    id: [],
    venteIdentFac: [],
    venteDateEffet: [],
    venteDateUpdate: [],
    venteStatusFact: [],
    venteMontantHT: [],
    venteMontantTVA: [],
    venteMontantTTC: [],
    livraisonCl: [],
  });

  constructor(
    protected factureVenteService: FactureVenteService,
    protected livraisonClService: LivraisonClService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureVente }) => {
      this.updateForm(factureVente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factureVente = this.createFromForm();
    if (factureVente.id !== undefined) {
      this.subscribeToSaveResponse(this.factureVenteService.update(factureVente));
    } else {
      this.subscribeToSaveResponse(this.factureVenteService.create(factureVente));
    }
  }

  trackLivraisonClById(index: number, item: ILivraisonCl): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactureVente>>): void {
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

  protected updateForm(factureVente: IFactureVente): void {
    this.editForm.patchValue({
      id: factureVente.id,
      venteIdentFac: factureVente.venteIdentFac,
      venteDateEffet: factureVente.venteDateEffet,
      venteDateUpdate: factureVente.venteDateUpdate,
      venteStatusFact: factureVente.venteStatusFact,
      venteMontantHT: factureVente.venteMontantHT,
      venteMontantTVA: factureVente.venteMontantTVA,
      venteMontantTTC: factureVente.venteMontantTTC,
      livraisonCl: factureVente.livraisonCl,
    });

    this.livraisonClsSharedCollection = this.livraisonClService.addLivraisonClToCollectionIfMissing(
      this.livraisonClsSharedCollection,
      factureVente.livraisonCl
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

  protected createFromForm(): IFactureVente {
    return {
      ...new FactureVente(),
      id: this.editForm.get(['id'])!.value,
      venteIdentFac: this.editForm.get(['venteIdentFac'])!.value,
      venteDateEffet: this.editForm.get(['venteDateEffet'])!.value,
      venteDateUpdate: this.editForm.get(['venteDateUpdate'])!.value,
      venteStatusFact: this.editForm.get(['venteStatusFact'])!.value,
      venteMontantHT: this.editForm.get(['venteMontantHT'])!.value,
      venteMontantTVA: this.editForm.get(['venteMontantTVA'])!.value,
      venteMontantTTC: this.editForm.get(['venteMontantTTC'])!.value,
      livraisonCl: this.editForm.get(['livraisonCl'])!.value,
    };
  }
}
