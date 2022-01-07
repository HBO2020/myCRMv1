import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PayementFournisseurComponent } from './list/payement-fournisseur.component';
import { PayementFournisseurDetailComponent } from './detail/payement-fournisseur-detail.component';
import { PayementFournisseurUpdateComponent } from './update/payement-fournisseur-update.component';
import { PayementFournisseurDeleteDialogComponent } from './delete/payement-fournisseur-delete-dialog.component';
import { PayementFournisseurRoutingModule } from './route/payement-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, PayementFournisseurRoutingModule],
  declarations: [
    PayementFournisseurComponent,
    PayementFournisseurDetailComponent,
    PayementFournisseurUpdateComponent,
    PayementFournisseurDeleteDialogComponent,
  ],
  entryComponents: [PayementFournisseurDeleteDialogComponent],
})
export class PayementFournisseurModule {}
