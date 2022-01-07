import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneLivFournisseur } from '../ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from '../service/ligne-liv-fournisseur.service';
import { LigneLivFournisseurDeleteDialogComponent } from '../delete/ligne-liv-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-liv-fournisseur',
  templateUrl: './ligne-liv-fournisseur.component.html',
})
export class LigneLivFournisseurComponent implements OnInit {
  ligneLivFournisseurs?: ILigneLivFournisseur[];
  isLoading = false;

  constructor(protected ligneLivFournisseurService: LigneLivFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ligneLivFournisseurService.query().subscribe({
      next: (res: HttpResponse<ILigneLivFournisseur[]>) => {
        this.isLoading = false;
        this.ligneLivFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILigneLivFournisseur): number {
    return item.id!;
  }

  delete(ligneLivFournisseur: ILigneLivFournisseur): void {
    const modalRef = this.modalService.open(LigneLivFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneLivFournisseur = ligneLivFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
