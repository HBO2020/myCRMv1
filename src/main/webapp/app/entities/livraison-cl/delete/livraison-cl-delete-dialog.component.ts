import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonCl } from '../livraison-cl.model';
import { LivraisonClService } from '../service/livraison-cl.service';

@Component({
  templateUrl: './livraison-cl-delete-dialog.component.html',
})
export class LivraisonClDeleteDialogComponent {
  livraisonCl?: ILivraisonCl;

  constructor(protected livraisonClService: LivraisonClService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livraisonClService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
