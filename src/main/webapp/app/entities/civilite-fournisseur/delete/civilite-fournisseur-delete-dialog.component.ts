import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiviliteFournisseur } from '../civilite-fournisseur.model';
import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';

@Component({
  templateUrl: './civilite-fournisseur-delete-dialog.component.html',
})
export class CiviliteFournisseurDeleteDialogComponent {
  civiliteFournisseur?: ICiviliteFournisseur;

  constructor(protected civiliteFournisseurService: CiviliteFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.civiliteFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
