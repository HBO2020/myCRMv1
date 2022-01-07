import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactFournisseur } from '../contact-fournisseur.model';
import { ContactFournisseurService } from '../service/contact-fournisseur.service';

@Component({
  templateUrl: './contact-fournisseur-delete-dialog.component.html',
})
export class ContactFournisseurDeleteDialogComponent {
  contactFournisseur?: IContactFournisseur;

  constructor(protected contactFournisseurService: ContactFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
