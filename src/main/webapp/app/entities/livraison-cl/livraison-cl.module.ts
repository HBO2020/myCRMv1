import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LivraisonClComponent } from './list/livraison-cl.component';
import { LivraisonClDetailComponent } from './detail/livraison-cl-detail.component';
import { LivraisonClUpdateComponent } from './update/livraison-cl-update.component';
import { LivraisonClDeleteDialogComponent } from './delete/livraison-cl-delete-dialog.component';
import { LivraisonClRoutingModule } from './route/livraison-cl-routing.module';

@NgModule({
  imports: [SharedModule, LivraisonClRoutingModule],
  declarations: [LivraisonClComponent, LivraisonClDetailComponent, LivraisonClUpdateComponent, LivraisonClDeleteDialogComponent],
  entryComponents: [LivraisonClDeleteDialogComponent],
})
export class LivraisonClModule {}
