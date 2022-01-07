import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayementClient } from '../payement-client.model';
import { PayementClientService } from '../service/payement-client.service';
import { PayementClientDeleteDialogComponent } from '../delete/payement-client-delete-dialog.component';

@Component({
  selector: 'jhi-payement-client',
  templateUrl: './payement-client.component.html',
})
export class PayementClientComponent implements OnInit {
  payementClients?: IPayementClient[];
  isLoading = false;

  constructor(protected payementClientService: PayementClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.payementClientService.query().subscribe({
      next: (res: HttpResponse<IPayementClient[]>) => {
        this.isLoading = false;
        this.payementClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPayementClient): number {
    return item.id!;
  }

  delete(payementClient: IPayementClient): void {
    const modalRef = this.modalService.open(PayementClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payementClient = payementClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
