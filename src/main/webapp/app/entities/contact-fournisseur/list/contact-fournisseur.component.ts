import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactFournisseur } from '../contact-fournisseur.model';
import { ContactFournisseurService } from '../service/contact-fournisseur.service';
import { ContactFournisseurDeleteDialogComponent } from '../delete/contact-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-contact-fournisseur',
  templateUrl: './contact-fournisseur.component.html',
})
export class ContactFournisseurComponent implements OnInit {
  contactFournisseurs?: IContactFournisseur[];
  isLoading = false;

  constructor(protected contactFournisseurService: ContactFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.contactFournisseurService.query().subscribe({
      next: (res: HttpResponse<IContactFournisseur[]>) => {
        this.isLoading = false;
        this.contactFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IContactFournisseur): number {
    return item.id!;
  }

  delete(contactFournisseur: IContactFournisseur): void {
    const modalRef = this.modalService.open(ContactFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactFournisseur = contactFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
