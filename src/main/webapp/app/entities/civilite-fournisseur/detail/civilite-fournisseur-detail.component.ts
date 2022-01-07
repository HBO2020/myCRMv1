import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICiviliteFournisseur } from '../civilite-fournisseur.model';

@Component({
  selector: 'jhi-civilite-fournisseur-detail',
  templateUrl: './civilite-fournisseur-detail.component.html',
})
export class CiviliteFournisseurDetailComponent implements OnInit {
  civiliteFournisseur: ICiviliteFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civiliteFournisseur }) => {
      this.civiliteFournisseur = civiliteFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
