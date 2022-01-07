import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayementClient } from '../payement-client.model';

@Component({
  selector: 'jhi-payement-client-detail',
  templateUrl: './payement-client-detail.component.html',
})
export class PayementClientDetailComponent implements OnInit {
  payementClient: IPayementClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payementClient }) => {
      this.payementClient = payementClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
