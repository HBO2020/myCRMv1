import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayementFournisseur } from '../payement-fournisseur.model';
import { PayementFournisseurService } from '../service/payement-fournisseur.service';
import { PayementFournisseurDeleteDialogComponent } from '../delete/payement-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-payement-fournisseur',
  templateUrl: './payement-fournisseur.component.html',
})
export class PayementFournisseurComponent implements OnInit {
  payementFournisseurs?: IPayementFournisseur[];
  isLoading = false;

  constructor(protected payementFournisseurService: PayementFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.payementFournisseurService.query().subscribe({
      next: (res: HttpResponse<IPayementFournisseur[]>) => {
        this.isLoading = false;
        this.payementFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPayementFournisseur): number {
    return item.id!;
  }

  delete(payementFournisseur: IPayementFournisseur): void {
    const modalRef = this.modalService.open(PayementFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payementFournisseur = payementFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
