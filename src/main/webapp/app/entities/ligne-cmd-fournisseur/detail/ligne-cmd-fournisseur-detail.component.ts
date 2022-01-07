import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';

@Component({
  selector: 'jhi-ligne-cmd-fournisseur-detail',
  templateUrl: './ligne-cmd-fournisseur-detail.component.html',
})
export class LigneCmdFournisseurDetailComponent implements OnInit {
  ligneCmdFournisseur: ILigneCmdFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCmdFournisseur }) => {
      this.ligneCmdFournisseur = ligneCmdFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
