import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneLivFournisseur } from '../ligne-liv-fournisseur.model';

@Component({
  selector: 'jhi-ligne-liv-fournisseur-detail',
  templateUrl: './ligne-liv-fournisseur-detail.component.html',
})
export class LigneLivFournisseurDetailComponent implements OnInit {
  ligneLivFournisseur: ILigneLivFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivFournisseur }) => {
      this.ligneLivFournisseur = ligneLivFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
