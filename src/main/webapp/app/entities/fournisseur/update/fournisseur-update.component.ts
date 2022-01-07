import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFournisseur, Fournisseur } from '../fournisseur.model';
import { FournisseurService } from '../service/fournisseur.service';
import { ICiviliteFournisseur } from 'app/entities/civilite-fournisseur/civilite-fournisseur.model';
import { CiviliteFournisseurService } from 'app/entities/civilite-fournisseur/service/civilite-fournisseur.service';

@Component({
  selector: 'jhi-fournisseur-update',
  templateUrl: './fournisseur-update.component.html',
})
export class FournisseurUpdateComponent implements OnInit {
  isSaving = false;

  civiliteFournisseursSharedCollection: ICiviliteFournisseur[] = [];

  editForm = this.fb.group({
    id: [],
    frIdent: [],
    frRaisonSocial: [],
    frAdresse: [],
    frCodePostal: [],
    frVille: [],
    frCountry: [],
    frEmail: [],
    frNumeroMobile: [],
    frNumeroFax: [],
    frNumeroFixe: [],
    frDateCreation: [],
    frDateUpdate: [],
    frStatus: [],
    frNumeroSiret: [],
    civilitefr: [],
  });

  constructor(
    protected fournisseurService: FournisseurService,
    protected civiliteFournisseurService: CiviliteFournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournisseur }) => {
      this.updateForm(fournisseur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fournisseur = this.createFromForm();
    if (fournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.fournisseurService.update(fournisseur));
    } else {
      this.subscribeToSaveResponse(this.fournisseurService.create(fournisseur));
    }
  }

  trackCiviliteFournisseurById(index: number, item: ICiviliteFournisseur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseur>>): void {
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

  protected updateForm(fournisseur: IFournisseur): void {
    this.editForm.patchValue({
      id: fournisseur.id,
      frIdent: fournisseur.frIdent,
      frRaisonSocial: fournisseur.frRaisonSocial,
      frAdresse: fournisseur.frAdresse,
      frCodePostal: fournisseur.frCodePostal,
      frVille: fournisseur.frVille,
      frCountry: fournisseur.frCountry,
      frEmail: fournisseur.frEmail,
      frNumeroMobile: fournisseur.frNumeroMobile,
      frNumeroFax: fournisseur.frNumeroFax,
      frNumeroFixe: fournisseur.frNumeroFixe,
      frDateCreation: fournisseur.frDateCreation,
      frDateUpdate: fournisseur.frDateUpdate,
      frStatus: fournisseur.frStatus,
      frNumeroSiret: fournisseur.frNumeroSiret,
      civilitefr: fournisseur.civilitefr,
    });

    this.civiliteFournisseursSharedCollection = this.civiliteFournisseurService.addCiviliteFournisseurToCollectionIfMissing(
      this.civiliteFournisseursSharedCollection,
      fournisseur.civilitefr
    );
  }

  protected loadRelationshipsOptions(): void {
    this.civiliteFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ICiviliteFournisseur[]>) => res.body ?? []))
      .pipe(
        map((civiliteFournisseurs: ICiviliteFournisseur[]) =>
          this.civiliteFournisseurService.addCiviliteFournisseurToCollectionIfMissing(
            civiliteFournisseurs,
            this.editForm.get('civilitefr')!.value
          )
        )
      )
      .subscribe((civiliteFournisseurs: ICiviliteFournisseur[]) => (this.civiliteFournisseursSharedCollection = civiliteFournisseurs));
  }

  protected createFromForm(): IFournisseur {
    return {
      ...new Fournisseur(),
      id: this.editForm.get(['id'])!.value,
      frIdent: this.editForm.get(['frIdent'])!.value,
      frRaisonSocial: this.editForm.get(['frRaisonSocial'])!.value,
      frAdresse: this.editForm.get(['frAdresse'])!.value,
      frCodePostal: this.editForm.get(['frCodePostal'])!.value,
      frVille: this.editForm.get(['frVille'])!.value,
      frCountry: this.editForm.get(['frCountry'])!.value,
      frEmail: this.editForm.get(['frEmail'])!.value,
      frNumeroMobile: this.editForm.get(['frNumeroMobile'])!.value,
      frNumeroFax: this.editForm.get(['frNumeroFax'])!.value,
      frNumeroFixe: this.editForm.get(['frNumeroFixe'])!.value,
      frDateCreation: this.editForm.get(['frDateCreation'])!.value,
      frDateUpdate: this.editForm.get(['frDateUpdate'])!.value,
      frStatus: this.editForm.get(['frStatus'])!.value,
      frNumeroSiret: this.editForm.get(['frNumeroSiret'])!.value,
      civilitefr: this.editForm.get(['civilitefr'])!.value,
    };
  }
}
