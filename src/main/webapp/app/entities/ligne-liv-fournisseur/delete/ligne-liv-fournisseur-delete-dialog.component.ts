import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';

@Component({
  templateUrl: './ligne-liv-fournisseur-delete-dialog.component.html',
})
export class LigneLivFournisseurDeleteDialogComponent {
  ligneLivFournisseur?: ILigneLivFournisseur;

  constructor(protected ligneLivFournisseurService: LigneLivFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneLivFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
