import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LigneLivFournisseurComponent } from './list/ligne-liv-fournisseur.component';
import { LigneLivFournisseurDetailComponent } from './detail/ligne-liv-fournisseur-detail.component';
import { LigneLivFournisseurUpdateComponent } from './update/ligne-liv-fournisseur-update.component';
import { LigneLivFournisseurDeleteDialogComponent } from './delete/ligne-liv-fournisseur-delete-dialog.component';
import { LigneLivFournisseurRoutingModule } from './route/ligne-liv-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, LigneLivFournisseurRoutingModule],
  declarations: [
    LigneLivFournisseurComponent,
    LigneLivFournisseurDetailComponent,
    LigneLivFournisseurUpdateComponent,
    LigneLivFournisseurDeleteDialogComponent,
  ],
  entryComponents: [LigneLivFournisseurDeleteDialogComponent],
})
export class LigneLivFournisseurModule {}
