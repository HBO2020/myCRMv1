import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUniteArticle } from '../unite-article.model';

@Component({
  selector: 'jhi-unite-article-detail',
  templateUrl: './unite-article-detail.component.html',
})
export class UniteArticleDetailComponent implements OnInit {
  uniteArticle: IUniteArticle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniteArticle }) => {
      this.uniteArticle = uniteArticle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
