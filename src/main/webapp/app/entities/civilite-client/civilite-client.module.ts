import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CiviliteClientComponent } from './list/civilite-client.component';
import { CiviliteClientDetailComponent } from './detail/civilite-client-detail.component';
import { CiviliteClientUpdateComponent } from './update/civilite-client-update.component';
import { CiviliteClientDeleteDialogComponent } from './delete/civilite-client-delete-dialog.component';
import { CiviliteClientRoutingModule } from './route/civilite-client-routing.module';

@NgModule({
  imports: [SharedModule, CiviliteClientRoutingModule],
  declarations: [
    CiviliteClientComponent,
    CiviliteClientDetailComponent,
    CiviliteClientUpdateComponent,
    CiviliteClientDeleteDialogComponent,
  ],
  entryComponents: [CiviliteClientDeleteDialogComponent],
})
export class CiviliteClientModule {}
