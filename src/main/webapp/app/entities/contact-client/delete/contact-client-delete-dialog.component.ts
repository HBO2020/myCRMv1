import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactClient } from '../contact-client.model';
import { ContactClientService } from '../service/contact-client.service';

@Component({
  templateUrl: './contact-client-delete-dialog.component.html',
})
export class ContactClientDeleteDialogComponent {
  contactClient?: IContactClient;

  constructor(protected contactClientService: ContactClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
