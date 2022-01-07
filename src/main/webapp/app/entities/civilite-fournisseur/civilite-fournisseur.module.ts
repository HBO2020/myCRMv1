import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CiviliteFournisseurComponent } from './list/civilite-fournisseur.component';
import { CiviliteFournisseurDetailComponent } from './detail/civilite-fournisseur-detail.component';
import { CiviliteFournisseurUpdateComponent } from './update/civilite-fournisseur-update.component';
import { CiviliteFournisseurDeleteDialogComponent } from './delete/civilite-fournisseur-delete-dialog.component';
import { CiviliteFournisseurRoutingModule } from './route/civilite-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, CiviliteFournisseurRoutingModule],
  declarations: [
    CiviliteFournisseurComponent,
    CiviliteFournisseurDetailComponent,
    CiviliteFournisseurUpdateComponent,
    CiviliteFournisseurDeleteDialogComponent,
  ],
  entryComponents: [CiviliteFournisseurDeleteDialogComponent],
})
export class CiviliteFournisseurModule {}
