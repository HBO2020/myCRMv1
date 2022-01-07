import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonCl } from '../livraison-cl.model';
import { LivraisonClService } from '../service/livraison-cl.service';
import { LivraisonClDeleteDialogComponent } from '../delete/livraison-cl-delete-dialog.component';

@Component({
  selector: 'jhi-livraison-cl',
  templateUrl: './livraison-cl.component.html',
})
export class LivraisonClComponent implements OnInit {
  livraisonCls?: ILivraisonCl[];
  isLoading = false;

  constructor(protected livraisonClService: LivraisonClService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.livraisonClService.query().subscribe({
      next: (res: HttpResponse<ILivraisonCl[]>) => {
        this.isLoading = false;
        this.livraisonCls = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILivraisonCl): number {
    return item.id!;
  }

  delete(livraisonCl: ILivraisonCl): void {
    const modalRef = this.modalService.open(LivraisonClDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraisonCl = livraisonCl;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
