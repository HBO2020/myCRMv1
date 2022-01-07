import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiviliteClient } from '../civilite-client.model';
import { CiviliteClientService } from '../service/civilite-client.service';

@Component({
  templateUrl: './civilite-client-delete-dialog.component.html',
})
export class CiviliteClientDeleteDialogComponent {
  civiliteClient?: ICiviliteClient;

  constructor(protected civiliteClientService: CiviliteClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.civiliteClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
