import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContactClientComponent } from './list/contact-client.component';
import { ContactClientDetailComponent } from './detail/contact-client-detail.component';
import { ContactClientUpdateComponent } from './update/contact-client-update.component';
import { ContactClientDeleteDialogComponent } from './delete/contact-client-delete-dialog.component';
import { ContactClientRoutingModule } from './route/contact-client-routing.module';

@NgModule({
  imports: [SharedModule, ContactClientRoutingModule],
  declarations: [ContactClientComponent, ContactClientDetailComponent, ContactClientUpdateComponent, ContactClientDeleteDialogComponent],
  entryComponents: [ContactClientDeleteDialogComponent],
})
export class ContactClientModule {}
