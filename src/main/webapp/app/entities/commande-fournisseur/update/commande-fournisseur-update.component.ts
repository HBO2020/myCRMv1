import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICommandeFournisseur, CommandeFournisseur } from '../commande-fournisseur.model';
import { CommandeFournisseurService } from '../service/commande-fournisseur.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';
import { LivraisonFrService } from 'app/entities/livraison-fr/service/livraison-fr.service';

@Component({
  selector: 'jhi-commande-fournisseur-update',
  templateUrl: './commande-fournisseur-update.component.html',
})
export class CommandeFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  fournisseursSharedCollection: IFournisseur[] = [];
  livraisonFrsSharedCollection: ILivraisonFr[] = [];

  editForm = this.fb.group({
    id: [],
    cmdIdenFr: [],
    cmdDateEffetFr: [],
    cmdDateLivraisonFr: [],
    fournisseur: [],
    livraisonFr: [],
  });

  constructor(
    protected commandeFournisseurService: CommandeFournisseurService,
    protected fournisseurService: FournisseurService,
    protected livraisonFrService: LivraisonFrService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeFournisseur }) => {
      this.updateForm(commandeFournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeFournisseur = this.createFromForm();
    if (commandeFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeFournisseurService.update(commandeFournisseur));
    } else {
      this.subscribeToSaveResponse(this.commandeFournisseurService.create(commandeFournisseur));
    }
  }

  trackFournisseurById(index: number, item: IFournisseur): number {
    return item.id!;
  }

  trackLivraisonFrById(index: number, item: ILivraisonFr): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeFournisseur>>): void {
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

  protected updateForm(commandeFournisseur: ICommandeFournisseur): void {
    this.editForm.patchValue({
      id: commandeFournisseur.id,
      cmdIdenFr: commandeFournisseur.cmdIdenFr,
      cmdDateEffetFr: commandeFournisseur.cmdDateEffetFr,
      cmdDateLivraisonFr: commandeFournisseur.cmdDateLivraisonFr,
      fournisseur: commandeFournisseur.fournisseur,
      livraisonFr: commandeFournisseur.livraisonFr,
    });

    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      commandeFournisseur.fournisseur
    );
    this.livraisonFrsSharedCollection = this.livraisonFrService.addLivraisonFrToCollectionIfMissing(
      this.livraisonFrsSharedCollection,
      commandeFournisseur.livraisonFr
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));

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

  protected createFromForm(): ICommandeFournisseur {
    return {
      ...new CommandeFournisseur(),
      id: this.editForm.get(['id'])!.value,
      cmdIdenFr: this.editForm.get(['cmdIdenFr'])!.value,
      cmdDateEffetFr: this.editForm.get(['cmdDateEffetFr'])!.value,
      cmdDateLivraisonFr: this.editForm.get(['cmdDateLivraisonFr'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
      livraisonFr: this.editForm.get(['livraisonFr'])!.value,
    };
  }
}
