import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LivraisonFrComponent } from './list/livraison-fr.component';
import { LivraisonFrDetailComponent } from './detail/livraison-fr-detail.component';
import { LivraisonFrUpdateComponent } from './update/livraison-fr-update.component';
import { LivraisonFrDeleteDialogComponent } from './delete/livraison-fr-delete-dialog.component';
import { LivraisonFrRoutingModule } from './route/livraison-fr-routing.module';

@NgModule({
  imports: [SharedModule, LivraisonFrRoutingModule],
  declarations: [LivraisonFrComponent, LivraisonFrDetailComponent, LivraisonFrUpdateComponent, LivraisonFrDeleteDialogComponent],
  entryComponents: [LivraisonFrDeleteDialogComponent],
})
export class LivraisonFrModule {}
