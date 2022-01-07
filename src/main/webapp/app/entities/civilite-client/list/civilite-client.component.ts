import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiviliteClient } from '../civilite-client.model';
import { CiviliteClientService } from '../service/civilite-client.service';
import { CiviliteClientDeleteDialogComponent } from '../delete/civilite-client-delete-dialog.component';

@Component({
  selector: 'jhi-civilite-client',
  templateUrl: './civilite-client.component.html',
})
export class CiviliteClientComponent implements OnInit {
  civiliteClients?: ICiviliteClient[];
  isLoading = false;

  constructor(protected civiliteClientService: CiviliteClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.civiliteClientService.query().subscribe({
      next: (res: HttpResponse<ICiviliteClient[]>) => {
        this.isLoading = false;
        this.civiliteClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICiviliteClient): number {
    return item.id!;
  }

  delete(civiliteClient: ICiviliteClient): void {
    const modalRef = this.modalService.open(CiviliteClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.civiliteClient = civiliteClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
