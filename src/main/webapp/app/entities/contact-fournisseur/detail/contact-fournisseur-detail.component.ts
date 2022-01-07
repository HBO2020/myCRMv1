import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactFournisseur } from '../contact-fournisseur.model';

@Component({
  selector: 'jhi-contact-fournisseur-detail',
  templateUrl: './contact-fournisseur-detail.component.html',
})
export class ContactFournisseurDetailComponent implements OnInit {
  contactFournisseur: IContactFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactFournisseur }) => {
      this.contactFournisseur = contactFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
