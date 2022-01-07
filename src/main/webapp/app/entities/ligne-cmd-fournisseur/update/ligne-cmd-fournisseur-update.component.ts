import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILigneCmdFournisseur, LigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { CommandeFournisseurService } from 'app/entities/commande-fournisseur/service/commande-fournisseur.service';

@Component({
  selector: 'jhi-ligne-cmd-fournisseur-update',
  templateUrl: './ligne-cmd-fournisseur-update.component.html',
})
export class LigneCmdFournisseurUpdateComponent implements OnInit {
  isSaving = false;

  commandeFournisseursSharedCollection: ICommandeFournisseur[] = [];

  editForm = this.fb.group({
    id: [],
    cmdQnFr: [],
    cmdNmPieces: [],
    commandeFourniseur: [],
  });

  constructor(
    protected ligneCmdFournisseurService: LigneCmdFournisseurService,
    protected commandeFournisseurService: CommandeFournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCmdFournisseur }) => {
      this.updateForm(ligneCmdFournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneCmdFournisseur = this.createFromForm();
    if (ligneCmdFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneCmdFournisseurService.update(ligneCmdFournisseur));
    } else {
      this.subscribeToSaveResponse(this.ligneCmdFournisseurService.create(ligneCmdFournisseur));
    }
  }

  trackCommandeFournisseurById(index: number, item: ICommandeFournisseur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneCmdFournisseur>>): void {
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

  protected updateForm(ligneCmdFournisseur: ILigneCmdFournisseur): void {
    this.editForm.patchValue({
      id: ligneCmdFournisseur.id,
      cmdQnFr: ligneCmdFournisseur.cmdQnFr,
      cmdNmPieces: ligneCmdFournisseur.cmdNmPieces,
      commandeFourniseur: ligneCmdFournisseur.commandeFourniseur,
    });

    this.commandeFournisseursSharedCollection = this.commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing(
      this.commandeFournisseursSharedCollection,
      ligneCmdFournisseur.commandeFourniseur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.commandeFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ICommandeFournisseur[]>) => res.body ?? []))
      .pipe(
        map((commandeFournisseurs: ICommandeFournisseur[]) =>
          this.commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing(
            commandeFournisseurs,
            this.editForm.get('commandeFourniseur')!.value
          )
        )
      )
      .subscribe((commandeFournisseurs: ICommandeFournisseur[]) => (this.commandeFournisseursSharedCollection = commandeFournisseurs));
  }

  protected createFromForm(): ILigneCmdFournisseur {
    return {
      ...new LigneCmdFournisseur(),
      id: this.editForm.get(['id'])!.value,
      cmdQnFr: this.editForm.get(['cmdQnFr'])!.value,
      cmdNmPieces: this.editForm.get(['cmdNmPieces'])!.value,
      commandeFourniseur: this.editForm.get(['commandeFourniseur'])!.value,
    };
  }
}
