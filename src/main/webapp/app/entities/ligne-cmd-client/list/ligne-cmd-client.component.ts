import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCmdClient } from '../ligne-cmd-client.model';
import { LigneCmdClientService } from '../service/ligne-cmd-client.service';
import { LigneCmdClientDeleteDialogComponent } from '../delete/ligne-cmd-client-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-cmd-client',
  templateUrl: './ligne-cmd-client.component.html',
})
export class LigneCmdClientComponent implements OnInit {
  ligneCmdClients?: ILigneCmdClient[];
  isLoading = false;

  constructor(protected ligneCmdClientService: LigneCmdClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ligneCmdClientService.query().subscribe({
      next: (res: HttpResponse<ILigneCmdClient[]>) => {
        this.isLoading = false;
        this.ligneCmdClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILigneCmdClient): number {
    return item.id!;
  }

  delete(ligneCmdClient: ILigneCmdClient): void {
    const modalRef = this.modalService.open(LigneCmdClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneCmdClient = ligneCmdClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
