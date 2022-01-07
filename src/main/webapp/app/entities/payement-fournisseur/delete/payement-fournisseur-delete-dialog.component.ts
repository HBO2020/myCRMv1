import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayementFournisseur } from '../payement-fournisseur.model';
import { PayementFournisseurService } from '../service/payement-fournisseur.service';

@Component({
  templateUrl: './payement-fournisseur-delete-dialog.component.html',
})
export class PayementFournisseurDeleteDialogComponent {
  payementFournisseur?: IPayementFournisseur;

  constructor(protected payementFournisseurService: PayementFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payementFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
