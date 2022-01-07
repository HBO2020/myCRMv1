import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUniteArticle } from '../unite-article.model';
import { UniteArticleService } from '../service/unite-article.service';

@Component({
  templateUrl: './unite-article-delete-dialog.component.html',
})
export class UniteArticleDeleteDialogComponent {
  uniteArticle?: IUniteArticle;

  constructor(protected uniteArticleService: UniteArticleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uniteArticleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
