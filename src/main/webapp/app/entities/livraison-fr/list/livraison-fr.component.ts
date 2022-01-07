import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonFr } from '../livraison-fr.model';
import { LivraisonFrService } from '../service/livraison-fr.service';
import { LivraisonFrDeleteDialogComponent } from '../delete/livraison-fr-delete-dialog.component';

@Component({
  selector: 'jhi-livraison-fr',
  templateUrl: './livraison-fr.component.html',
})
export class LivraisonFrComponent implements OnInit {
  livraisonFrs?: ILivraisonFr[];
  isLoading = false;

  constructor(protected livraisonFrService: LivraisonFrService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.livraisonFrService.query().subscribe({
      next: (res: HttpResponse<ILivraisonFr[]>) => {
        this.isLoading = false;
        this.livraisonFrs = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILivraisonFr): number {
    return item.id!;
  }

  delete(livraisonFr: ILivraisonFr): void {
    const modalRef = this.modalService.open(LivraisonFrDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraisonFr = livraisonFr;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
