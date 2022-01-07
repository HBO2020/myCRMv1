import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonFr } from '../livraison-fr.model';
import { LivraisonFrService } from '../service/livraison-fr.service';

@Component({
  templateUrl: './livraison-fr-delete-dialog.component.html',
})
export class LivraisonFrDeleteDialogComponent {
  livraisonFr?: ILivraisonFr;

  constructor(protected livraisonFrService: LivraisonFrService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livraisonFrService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
