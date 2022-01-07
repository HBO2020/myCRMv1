import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactClient } from '../contact-client.model';

@Component({
  selector: 'jhi-contact-client-detail',
  templateUrl: './contact-client-detail.component.html',
})
export class ContactClientDetailComponent implements OnInit {
  contactClient: IContactClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactClient }) => {
      this.contactClient = contactClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
