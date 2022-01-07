import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarts } from '../carts.model';

@Component({
  selector: 'jhi-carts-detail',
  templateUrl: './carts-detail.component.html',
})
export class CartsDetailComponent implements OnInit {
  carts: ICarts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carts }) => {
      this.carts = carts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
