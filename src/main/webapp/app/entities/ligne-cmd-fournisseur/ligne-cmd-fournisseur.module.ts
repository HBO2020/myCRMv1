import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LigneCmdFournisseurComponent } from './list/ligne-cmd-fournisseur.component';
import { LigneCmdFournisseurDetailComponent } from './detail/ligne-cmd-fournisseur-detail.component';
import { LigneCmdFournisseurUpdateComponent } from './update/ligne-cmd-fournisseur-update.component';
import { LigneCmdFournisseurDeleteDialogComponent } from './delete/ligne-cmd-fournisseur-delete-dialog.component';
import { LigneCmdFournisseurRoutingModule } from './route/ligne-cmd-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, LigneCmdFournisseurRoutingModule],
  declarations: [
    LigneCmdFournisseurComponent,
    LigneCmdFournisseurDetailComponent,
    LigneCmdFournisseurUpdateComponent,
    LigneCmdFournisseurDeleteDialogComponent,
  ],
  entryComponents: [LigneCmdFournisseurDeleteDialogComponent],
})
export class LigneCmdFournisseurModule {}
