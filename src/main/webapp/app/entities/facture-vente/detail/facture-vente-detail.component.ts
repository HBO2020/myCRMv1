import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactureVente } from '../facture-vente.model';

@Component({
  selector: 'jhi-facture-vente-detail',
  templateUrl: './facture-vente-detail.component.html',
})
export class FactureVenteDetailComponent implements OnInit {
  factureVente: IFactureVente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureVente }) => {
      this.factureVente = factureVente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
