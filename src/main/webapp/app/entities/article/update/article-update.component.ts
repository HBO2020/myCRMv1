import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IArticle, Article } from '../article.model';
import { ArticleService } from '../service/article.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILigneCmdFournisseur } from 'app/entities/ligne-cmd-fournisseur/ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from 'app/entities/ligne-cmd-fournisseur/service/ligne-cmd-fournisseur.service';
import { ILigneLivFournisseur } from 'app/entities/ligne-liv-fournisseur/ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from 'app/entities/ligne-liv-fournisseur/service/ligne-liv-fournisseur.service';
import { IUniteArticle } from 'app/entities/unite-article/unite-article.model';
import { UniteArticleService } from 'app/entities/unite-article/service/unite-article.service';
import { ILigneCmdClient } from 'app/entities/ligne-cmd-client/ligne-cmd-client.model';
import { LigneCmdClientService } from 'app/entities/ligne-cmd-client/service/ligne-cmd-client.service';
import { ILigneLivClient } from 'app/entities/ligne-liv-client/ligne-liv-client.model';
import { LigneLivClientService } from 'app/entities/ligne-liv-client/service/ligne-liv-client.service';

@Component({
  selector: 'jhi-article-update',
  templateUrl: './article-update.component.html',
})
export class ArticleUpdateComponent implements OnInit {
  isSaving = false;

  ligneCmdFournisseursSharedCollection: ILigneCmdFournisseur[] = [];
  ligneLivFournisseursSharedCollection: ILigneLivFournisseur[] = [];
  uniteArticlesSharedCollection: IUniteArticle[] = [];
  ligneCmdClientsSharedCollection: ILigneCmdClient[] = [];
  ligneLivClientsSharedCollection: ILigneLivClient[] = [];

  editForm = this.fb.group({
    id: [],
    artclIden: [],
    artclReference: [],
    artclDesignation: [],
    artclQnStock: [],
    artclImg: [],
    artclImgContentType: [],
    artclSerie: [],
    artclPrixAchat: [],
    artclPxAchatTotal: [],
    artclPxVenteTotal: [],
    ligneCmdFournisseur: [],
    ligneLivFournisseur: [],
    uniteArticle: [],
    ligneCmdClient: [],
    ligneLivClient: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected articleService: ArticleService,
    protected ligneCmdFournisseurService: LigneCmdFournisseurService,
    protected ligneLivFournisseurService: LigneLivFournisseurService,
    protected uniteArticleService: UniteArticleService,
    protected ligneCmdClientService: LigneCmdClientService,
    protected ligneLivClientService: LigneLivClientService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ article }) => {
      this.updateForm(article);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('myCrMv1App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const article = this.createFromForm();
    if (article.id !== undefined) {
      this.subscribeToSaveResponse(this.articleService.update(article));
    } else {
      this.subscribeToSaveResponse(this.articleService.create(article));
    }
  }

  trackLigneCmdFournisseurById(index: number, item: ILigneCmdFournisseur): number {
    return item.id!;
  }

  trackLigneLivFournisseurById(index: number, item: ILigneLivFournisseur): number {
    return item.id!;
  }

  trackUniteArticleById(index: number, item: IUniteArticle): number {
    return item.id!;
  }

  trackLigneCmdClientById(index: number, item: ILigneCmdClient): number {
    return item.id!;
  }

  trackLigneLivClientById(index: number, item: ILigneLivClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticle>>): void {
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

  protected updateForm(article: IArticle): void {
    this.editForm.patchValue({
      id: article.id,
      artclIden: article.artclIden,
      artclReference: article.artclReference,
      artclDesignation: article.artclDesignation,
      artclQnStock: article.artclQnStock,
      artclImg: article.artclImg,
      artclImgContentType: article.artclImgContentType,
      artclSerie: article.artclSerie,
      artclPrixAchat: article.artclPrixAchat,
      artclPxAchatTotal: article.artclPxAchatTotal,
      artclPxVenteTotal: article.artclPxVenteTotal,
      ligneCmdFournisseur: article.ligneCmdFournisseur,
      ligneLivFournisseur: article.ligneLivFournisseur,
      uniteArticle: article.uniteArticle,
      ligneCmdClient: article.ligneCmdClient,
      ligneLivClient: article.ligneLivClient,
    });

    this.ligneCmdFournisseursSharedCollection = this.ligneCmdFournisseurService.addLigneCmdFournisseurToCollectionIfMissing(
      this.ligneCmdFournisseursSharedCollection,
      article.ligneCmdFournisseur
    );
    this.ligneLivFournisseursSharedCollection = this.ligneLivFournisseurService.addLigneLivFournisseurToCollectionIfMissing(
      this.ligneLivFournisseursSharedCollection,
      article.ligneLivFournisseur
    );
    this.uniteArticlesSharedCollection = this.uniteArticleService.addUniteArticleToCollectionIfMissing(
      this.uniteArticlesSharedCollection,
      article.uniteArticle
    );
    this.ligneCmdClientsSharedCollection = this.ligneCmdClientService.addLigneCmdClientToCollectionIfMissing(
      this.ligneCmdClientsSharedCollection,
      article.ligneCmdClient
    );
    this.ligneLivClientsSharedCollection = this.ligneLivClientService.addLigneLivClientToCollectionIfMissing(
      this.ligneLivClientsSharedCollection,
      article.ligneLivClient
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ligneCmdFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ILigneCmdFournisseur[]>) => res.body ?? []))
      .pipe(
        map((ligneCmdFournisseurs: ILigneCmdFournisseur[]) =>
          this.ligneCmdFournisseurService.addLigneCmdFournisseurToCollectionIfMissing(
            ligneCmdFournisseurs,
            this.editForm.get('ligneCmdFournisseur')!.value
          )
        )
      )
      .subscribe((ligneCmdFournisseurs: ILigneCmdFournisseur[]) => (this.ligneCmdFournisseursSharedCollection = ligneCmdFournisseurs));

    this.ligneLivFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ILigneLivFournisseur[]>) => res.body ?? []))
      .pipe(
        map((ligneLivFournisseurs: ILigneLivFournisseur[]) =>
          this.ligneLivFournisseurService.addLigneLivFournisseurToCollectionIfMissing(
            ligneLivFournisseurs,
            this.editForm.get('ligneLivFournisseur')!.value
          )
        )
      )
      .subscribe((ligneLivFournisseurs: ILigneLivFournisseur[]) => (this.ligneLivFournisseursSharedCollection = ligneLivFournisseurs));

    this.uniteArticleService
      .query()
      .pipe(map((res: HttpResponse<IUniteArticle[]>) => res.body ?? []))
      .pipe(
        map((uniteArticles: IUniteArticle[]) =>
          this.uniteArticleService.addUniteArticleToCollectionIfMissing(uniteArticles, this.editForm.get('uniteArticle')!.value)
        )
      )
      .subscribe((uniteArticles: IUniteArticle[]) => (this.uniteArticlesSharedCollection = uniteArticles));

    this.ligneCmdClientService
      .query()
      .pipe(map((res: HttpResponse<ILigneCmdClient[]>) => res.body ?? []))
      .pipe(
        map((ligneCmdClients: ILigneCmdClient[]) =>
          this.ligneCmdClientService.addLigneCmdClientToCollectionIfMissing(ligneCmdClients, this.editForm.get('ligneCmdClient')!.value)
        )
      )
      .subscribe((ligneCmdClients: ILigneCmdClient[]) => (this.ligneCmdClientsSharedCollection = ligneCmdClients));

    this.ligneLivClientService
      .query()
      .pipe(map((res: HttpResponse<ILigneLivClient[]>) => res.body ?? []))
      .pipe(
        map((ligneLivClients: ILigneLivClient[]) =>
          this.ligneLivClientService.addLigneLivClientToCollectionIfMissing(ligneLivClients, this.editForm.get('ligneLivClient')!.value)
        )
      )
      .subscribe((ligneLivClients: ILigneLivClient[]) => (this.ligneLivClientsSharedCollection = ligneLivClients));
  }

  protected createFromForm(): IArticle {
    return {
      ...new Article(),
      id: this.editForm.get(['id'])!.value,
      artclIden: this.editForm.get(['artclIden'])!.value,
      artclReference: this.editForm.get(['artclReference'])!.value,
      artclDesignation: this.editForm.get(['artclDesignation'])!.value,
      artclQnStock: this.editForm.get(['artclQnStock'])!.value,
      artclImgContentType: this.editForm.get(['artclImgContentType'])!.value,
      artclImg: this.editForm.get(['artclImg'])!.value,
      artclSerie: this.editForm.get(['artclSerie'])!.value,
      artclPrixAchat: this.editForm.get(['artclPrixAchat'])!.value,
      artclPxAchatTotal: this.editForm.get(['artclPxAchatTotal'])!.value,
      artclPxVenteTotal: this.editForm.get(['artclPxVenteTotal'])!.value,
      ligneCmdFournisseur: this.editForm.get(['ligneCmdFournisseur'])!.value,
      ligneLivFournisseur: this.editForm.get(['ligneLivFournisseur'])!.value,
      uniteArticle: this.editForm.get(['uniteArticle'])!.value,
      ligneCmdClient: this.editForm.get(['ligneCmdClient'])!.value,
      ligneLivClient: this.editForm.get(['ligneLivClient'])!.value,
    };
  }
}
