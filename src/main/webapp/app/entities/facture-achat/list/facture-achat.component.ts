import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactureAchat } from '../facture-achat.model';
import { FactureAchatService } from '../service/facture-achat.service';
import { FactureAchatDeleteDialogComponent } from '../delete/facture-achat-delete-dialog.component';

@Component({
  selector: 'jhi-facture-achat',
  templateUrl: './facture-achat.component.html',
})
export class FactureAchatComponent implements OnInit {
  factureAchats?: IFactureAchat[];
  isLoading = false;

  constructor(protected factureAchatService: FactureAchatService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.factureAchatService.query().subscribe({
      next: (res: HttpResponse<IFactureAchat[]>) => {
        this.isLoading = false;
        this.factureAchats = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFactureAchat): number {
    return item.id!;
  }

  delete(factureAchat: IFactureAchat): void {
    const modalRef = this.modalService.open(FactureAchatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.factureAchat = factureAchat;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
