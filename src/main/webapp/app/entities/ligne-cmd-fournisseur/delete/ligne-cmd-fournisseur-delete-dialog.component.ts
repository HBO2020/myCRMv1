import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';

@Component({
  templateUrl: './ligne-cmd-fournisseur-delete-dialog.component.html',
})
export class LigneCmdFournisseurDeleteDialogComponent {
  ligneCmdFournisseur?: ILigneCmdFournisseur;

  constructor(protected ligneCmdFournisseurService: LigneCmdFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneCmdFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
