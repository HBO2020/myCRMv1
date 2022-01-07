import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUniteArticle } from '../unite-article.model';
import { UniteArticleService } from '../service/unite-article.service';
import { UniteArticleDeleteDialogComponent } from '../delete/unite-article-delete-dialog.component';

@Component({
  selector: 'jhi-unite-article',
  templateUrl: './unite-article.component.html',
})
export class UniteArticleComponent implements OnInit {
  uniteArticles?: IUniteArticle[];
  isLoading = false;

  constructor(protected uniteArticleService: UniteArticleService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.uniteArticleService.query().subscribe({
      next: (res: HttpResponse<IUniteArticle[]>) => {
        this.isLoading = false;
        this.uniteArticles = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IUniteArticle): number {
    return item.id!;
  }

  delete(uniteArticle: IUniteArticle): void {
    const modalRef = this.modalService.open(UniteArticleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.uniteArticle = uniteArticle;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
