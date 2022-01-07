import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IUniteArticle, UniteArticle } from '../unite-article.model';
import { UniteArticleService } from '../service/unite-article.service';

@Component({
  selector: 'jhi-unite-article-update',
  templateUrl: './unite-article-update.component.html',
})
export class UniteArticleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uniteCode: [],
    uniteLibelle: [],
    uniteOption: [],
  });

  constructor(protected uniteArticleService: UniteArticleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniteArticle }) => {
      this.updateForm(uniteArticle);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uniteArticle = this.createFromForm();
    if (uniteArticle.id !== undefined) {
      this.subscribeToSaveResponse(this.uniteArticleService.update(uniteArticle));
    } else {
      this.subscribeToSaveResponse(this.uniteArticleService.create(uniteArticle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniteArticle>>): void {
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

  protected updateForm(uniteArticle: IUniteArticle): void {
    this.editForm.patchValue({
      id: uniteArticle.id,
      uniteCode: uniteArticle.uniteCode,
      uniteLibelle: uniteArticle.uniteLibelle,
      uniteOption: uniteArticle.uniteOption,
    });
  }

  protected createFromForm(): IUniteArticle {
    return {
      ...new UniteArticle(),
      id: this.editForm.get(['id'])!.value,
      uniteCode: this.editForm.get(['uniteCode'])!.value,
      uniteLibelle: this.editForm.get(['uniteLibelle'])!.value,
      uniteOption: this.editForm.get(['uniteOption'])!.value,
    };
  }
}
