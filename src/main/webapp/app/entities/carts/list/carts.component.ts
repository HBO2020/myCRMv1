import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarts } from '../carts.model';
import { CartsService } from '../service/carts.service';
import { CartsDeleteDialogComponent } from '../delete/carts-delete-dialog.component';

@Component({
  selector: 'jhi-carts',
  templateUrl: './carts.component.html',
})
export class CartsComponent implements OnInit {
  carts?: ICarts[];
  isLoading = false;

  constructor(protected cartsService: CartsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cartsService.query().subscribe({
      next: (res: HttpResponse<ICarts[]>) => {
        this.isLoading = false;
        this.carts = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICarts): number {
    return item.id!;
  }

  delete(carts: ICarts): void {
    const modalRef = this.modalService.open(CartsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carts = carts;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
