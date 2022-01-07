import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivraisonCl } from '../livraison-cl.model';

@Component({
  selector: 'jhi-livraison-cl-detail',
  templateUrl: './livraison-cl-detail.component.html',
})
export class LivraisonClDetailComponent implements OnInit {
  livraisonCl: ILivraisonCl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonCl }) => {
      this.livraisonCl = livraisonCl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
