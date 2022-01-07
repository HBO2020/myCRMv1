import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneCmdClient } from '../ligne-cmd-client.model';

@Component({
  selector: 'jhi-ligne-cmd-client-detail',
  templateUrl: './ligne-cmd-client-detail.component.html',
})
export class LigneCmdClientDetailComponent implements OnInit {
  ligneCmdClient: ILigneCmdClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCmdClient }) => {
      this.ligneCmdClient = ligneCmdClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
