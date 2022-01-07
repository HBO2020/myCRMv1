import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICiviliteClient } from '../civilite-client.model';

@Component({
  selector: 'jhi-civilite-client-detail',
  templateUrl: './civilite-client-detail.component.html',
})
export class CiviliteClientDetailComponent implements OnInit {
  civiliteClient: ICiviliteClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civiliteClient }) => {
      this.civiliteClient = civiliteClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
