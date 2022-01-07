import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneLivClient } from '../ligne-liv-client.model';
import { LigneLivClientService } from '../service/ligne-liv-client.service';

@Component({
  templateUrl: './ligne-liv-client-delete-dialog.component.html',
})
export class LigneLivClientDeleteDialogComponent {
  ligneLivClient?: ILigneLivClient;

  constructor(protected ligneLivClientService: LigneLivClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneLivClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
