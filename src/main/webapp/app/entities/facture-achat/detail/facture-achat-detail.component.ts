import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactureAchat } from '../facture-achat.model';

@Component({
  selector: 'jhi-facture-achat-detail',
  templateUrl: './facture-achat-detail.component.html',
})
export class FactureAchatDetailComponent implements OnInit {
  factureAchat: IFactureAchat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureAchat }) => {
      this.factureAchat = factureAchat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
