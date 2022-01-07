import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayementFournisseur } from '../payement-fournisseur.model';

@Component({
  selector: 'jhi-payement-fournisseur-detail',
  templateUrl: './payement-fournisseur-detail.component.html',
})
export class PayementFournisseurDetailComponent implements OnInit {
  payementFournisseur: IPayementFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payementFournisseur }) => {
      this.payementFournisseur = payementFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
