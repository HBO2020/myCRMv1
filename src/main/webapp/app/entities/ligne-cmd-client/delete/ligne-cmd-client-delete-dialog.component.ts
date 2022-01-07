import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCmdClient } from '../ligne-cmd-client.model';
import { LigneCmdClientService } from '../service/ligne-cmd-client.service';

@Component({
  templateUrl: './ligne-cmd-client-delete-dialog.component.html',
})
export class LigneCmdClientDeleteDialogComponent {
  ligneCmdClient?: ILigneCmdClient;

  constructor(protected ligneCmdClientService: LigneCmdClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneCmdClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
