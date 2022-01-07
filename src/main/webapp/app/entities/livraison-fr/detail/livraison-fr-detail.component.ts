import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivraisonFr } from '../livraison-fr.model';

@Component({
  selector: 'jhi-livraison-fr-detail',
  templateUrl: './livraison-fr-detail.component.html',
})
export class LivraisonFrDetailComponent implements OnInit {
  livraisonFr: ILivraisonFr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonFr }) => {
      this.livraisonFr = livraisonFr;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
