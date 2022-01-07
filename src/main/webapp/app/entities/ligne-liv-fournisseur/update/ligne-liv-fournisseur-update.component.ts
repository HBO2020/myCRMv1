import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILigneLivFournisseur, LigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';
import { LivraisonFrService } from 'app/entities/livraison-fr/service/livraison-fr.service';

@Component({
  selector: 'jhi-ligne-liv-fournisseur-update',
  templateUrl: './ligne-liv-fournisseur-update.component.html',
})
export class LigneLivFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  livraisonFrsSharedCollection: ILivraisonFr[] = [];

  editForm = this.fb.group({
    id: [],
    livFrQuantite: [],
    livFrNmPieces: [],
    livFrTotalPrix: [],
    livraisonFr: [],
  });

  constructor(
    protected ligneLivFournisseurService: LigneLivFournisseurService,
    protected livraisonFrService: LivraisonFrService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivFournisseur }) => {
      this.updateForm(ligneLivFournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneLivFournisseur = this.createFromForm();
    if (ligneLivFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneLivFournisseurService.update(ligneLivFournisseur));
    } else {
      this.subscribeToSaveResponse(this.ligneLivFournisseurService.create(ligneLivFournisseur));
    }
  }

  trackLivraisonFrById(index: number, item: ILivraisonFr): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneLivFournisseur>>): void {
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

  protected updateForm(ligneLivFournisseur: ILigneLivFournisseur): void {
    this.editForm.patchValue({
      id: ligneLivFournisseur.id,
      livFrQuantite: ligneLivFournisseur.livFrQuantite,
      livFrNmPieces: ligneLivFournisseur.livFrNmPieces,
      livFrTotalPrix: ligneLivFournisseur.livFrTotalPrix,
      livraisonFr: ligneLivFournisseur.livraisonFr,
    });

    this.livraisonFrsSharedCollection = this.livraisonFrService.addLivraisonFrToCollectionIfMissing(
      this.livraisonFrsSharedCollection,
      ligneLivFournisseur.livraisonFr
    );
  }

  protected loadRelationshipsOptions(): void {
    this.livraisonFrService
      .query()
      .pipe(map((res: HttpResponse<ILivraisonFr[]>) => res.body ?? []))
      .pipe(
        map((livraisonFrs: ILivraisonFr[]) =>
          this.livraisonFrService.addLivraisonFrToCollectionIfMissing(livraisonFrs, this.editForm.get('livraisonFr')!.value)
        )
      )
      .subscribe((livraisonFrs: ILivraisonFr[]) => (this.livraisonFrsSharedCollection = livraisonFrs));
  }

  protected createFromForm(): ILigneLivFournisseur {
    return {
      ...new LigneLivFournisseur(),
      id: this.editForm.get(['id'])!.value,
      livFrQuantite: this.editForm.get(['livFrQuantite'])!.value,
      livFrNmPieces: this.editForm.get(['livFrNmPieces'])!.value,
      livFrTotalPrix: this.editForm.get(['livFrTotalPrix'])!.value,
      livraisonFr: this.editForm.get(['livraisonFr'])!.value,
    };
  }
}
