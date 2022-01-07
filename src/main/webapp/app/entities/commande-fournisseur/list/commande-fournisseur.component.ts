import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeFournisseur } from '../commande-fournisseur.model';
import { CommandeFournisseurService } from '../service/commande-fournisseur.service';
import { CommandeFournisseurDeleteDialogComponent } from '../delete/commande-fournisseur-delete-dialog.component';

@Component({
  selector: 'jhi-commande-fournisseur',
  templateUrl: './commande-fournisseur.component.html',
})
export class CommandeFournisseurComponent implements OnInit {
  commandeFournisseurs?: ICommandeFournisseur[];
  isLoading = false;

  constructor(protected commandeFournisseurService: CommandeFournisseurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.commandeFournisseurService.query().subscribe({
      next: (res: HttpResponse<ICommandeFournisseur[]>) => {
        this.isLoading = false;
        this.commandeFournisseurs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICommandeFournisseur): number {
    return item.id!;
  }

  delete(commandeFournisseur: ICommandeFournisseur): void {
    const modalRef = this.modalService.open(CommandeFournisseurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeFournisseur = commandeFournisseur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
