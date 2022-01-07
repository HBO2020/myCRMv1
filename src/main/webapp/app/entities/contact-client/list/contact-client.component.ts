import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactClient } from '../contact-client.model';
import { ContactClientService } from '../service/contact-client.service';
import { ContactClientDeleteDialogComponent } from '../delete/contact-client-delete-dialog.component';

@Component({
  selector: 'jhi-contact-client',
  templateUrl: './contact-client.component.html',
})
export class ContactClientComponent implements OnInit {
  contactClients?: IContactClient[];
  isLoading = false;

  constructor(protected contactClientService: ContactClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.contactClientService.query().subscribe({
      next: (res: HttpResponse<IContactClient[]>) => {
        this.isLoading = false;
        this.contactClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IContactClient): number {
    return item.id!;
  }

  delete(contactClient: IContactClient): void {
    const modalRef = this.modalService.open(ContactClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactClient = contactClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
