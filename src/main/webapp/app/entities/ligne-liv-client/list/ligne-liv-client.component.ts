import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneLivClient } from '../ligne-liv-client.model';
import { LigneLivClientService } from '../service/ligne-liv-client.service';
import { LigneLivClientDeleteDialogComponent } from '../delete/ligne-liv-client-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-liv-client',
  templateUrl: './ligne-liv-client.component.html',
})
export class LigneLivClientComponent implements OnInit {
  ligneLivClients?: ILigneLivClient[];
  isLoading = false;

  constructor(protected ligneLivClientService: LigneLivClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ligneLivClientService.query().subscribe({
      next: (res: HttpResponse<ILigneLivClient[]>) => {
        this.isLoading = false;
        this.ligneLivClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILigneLivClient): number {
    return item.id!;
  }

  delete(ligneLivClient: ILigneLivClient): void {
    const modalRef = this.modalService.open(LigneLivClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneLivClient = ligneLivClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
