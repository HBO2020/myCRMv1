import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneLivClient } from '../ligne-liv-client.model';

@Component({
  selector: 'jhi-ligne-liv-client-detail',
  templateUrl: './ligne-liv-client-detail.component.html',
})
export class LigneLivClientDetailComponent implements OnInit {
  ligneLivClient: ILigneLivClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivClient }) => {
      this.ligneLivClient = ligneLivClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
