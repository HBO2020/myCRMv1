import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContactFournisseurComponent } from './list/contact-fournisseur.component';
import { ContactFournisseurDetailComponent } from './detail/contact-fournisseur-detail.component';
import { ContactFournisseurUpdateComponent } from './update/contact-fournisseur-update.component';
import { ContactFournisseurDeleteDialogComponent } from './delete/contact-fournisseur-delete-dialog.component';
import { ContactFournisseurRoutingModule } from './route/contact-fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, ContactFournisseurRoutingModule],
  declarations: [
    ContactFournisseurComponent,
    ContactFournisseurDetailComponent,
    ContactFournisseurUpdateComponent,
    ContactFournisseurDeleteDialogComponent,
  ],
  entryComponents: [ContactFournisseurDeleteDialogComponent],
})
export class ContactFournisseurModule {}
