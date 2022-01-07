import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCmdFournisseur } from '../ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from '../service/ligne-cmd-fournisseur.service';
import { LigneCmdFournisseurDeleteDialogComponent } from '../delete/ligne-cmd-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-cmd-fournisseur',
  templateUrl: './ligne-cmd-fournisseur.component.html',
})
export class LigneCmdFournisseurComponent implements OnInit {
  ligneCmdFournisseurs?: ILigneCmdFournisseur[];
  isLoading = false;

  constructor(protected ligneCmdFournisseurService: LigneCmdFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ligneCmdFournisseurService.query().subscribe({
      next: (res: HttpResponse<ILigneCmdFournisseur[]>) => {
        this.isLoading = false;
        this.ligneCmdFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILigneCmdFournisseur): number {
    return item.id!;
  }

  delete(ligneCmdFournisseur: ILigneCmdFournisseur): void {
    const modalRef = this.modalService.open(LigneCmdFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneCmdFournisseur = ligneCmdFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
