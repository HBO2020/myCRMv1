import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactureVente } from '../facture-vente.model';
import { FactureVenteService } from '../service/facture-vente.service';
import { FactureVenteDeleteDialogComponent } from '../delete/facture-vente-delete-dialog.component';

@Component({
  selector: 'jhi-facture-vente',
  templateUrl: './facture-vente.component.html',
})
export class FactureVenteComponent implements OnInit {
  factureVentes?: IFactureVente[];
  isLoading = false;

  constructor(protected factureVenteService: FactureVenteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.factureVenteService.query().subscribe({
      next: (res: HttpResponse<IFactureVente[]>) => {
        this.isLoading = false;
        this.factureVentes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFactureVente): number {
    return item.id!;
  }

  delete(factureVente: IFactureVente): void {
    const modalRef = this.modalService.open(FactureVenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.factureVente = factureVente;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
