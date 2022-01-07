import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarts } from '../carts.model';
import { CartsService } from '../service/carts.service';

@Component({
  templateUrl: './carts-delete-dialog.component.html',
})
export class CartsDeleteDialogComponent {
  carts?: ICarts;

  constructor(protected cartsService: CartsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cartsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
