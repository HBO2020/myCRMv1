import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiviliteFournisseur } from '../civilite-fournisseur.model';
import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';
import { CiviliteFournisseurDeleteDialogComponent } from '../delete/civilite-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-civilite-fournisseur',
  templateUrl: './civilite-fournisseur.component.html',
})
export class CiviliteFournisseurComponent implements OnInit {
  civiliteFournisseurs?: ICiviliteFournisseur[];
  isLoading = false;

  constructor(protected civiliteFournisseurService: CiviliteFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.civiliteFournisseurService.query().subscribe({
      next: (res: HttpResponse<ICiviliteFournisseur[]>) => {
        this.isLoading = false;
        this.civiliteFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICiviliteFournisseur): number {
    return item.id!;
  }

  delete(civiliteFournisseur: ICiviliteFournisseur): void {
    const modalRef = this.modalService.open(CiviliteFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.civiliteFournisseur = civiliteFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
