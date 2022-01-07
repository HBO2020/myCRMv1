import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';
import { CommandeClientDeleteDialogComponent } from '../delete/commande-client-delete-dialog.component';

@Component({
  selector: 'jhi-commande-client',
  templateUrl: './commande-client.component.html',
})
export class CommandeClientComponent implements OnInit {
  commandeClients?: ICommandeClient[];
  isLoading = false;

  constructor(protected commandeClientService: CommandeClientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.commandeClientService.query().subscribe({
      next: (res: HttpResponse<ICommandeClient[]>) => {
        this.isLoading = false;
        this.commandeClients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICommandeClient): number {
    return item.id!;
  }

  delete(commandeClient: ICommandeClient): void {
    const modalRef = this.modalService.open(CommandeClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeClient = commandeClient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
