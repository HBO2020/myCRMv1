import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayementClient } from '../payement-client.model';
import { PayementClientService } from '../service/payement-client.service';

@Component({
  templateUrl: './payement-client-delete-dialog.component.html',
})
export class PayementClientDeleteDialogComponent {
  payementClient?: IPayementClient;

  constructor(protected payementClientService: PayementClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payementClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
