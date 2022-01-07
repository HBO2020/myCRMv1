import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LigneLivClientComponent } from './list/ligne-liv-client.component';
import { LigneLivClientDetailComponent } from './detail/ligne-liv-client-detail.component';
import { LigneLivClientUpdateComponent } from './update/ligne-liv-client-update.component';
import { LigneLivClientDeleteDialogComponent } from './delete/ligne-liv-client-delete-dialog.component';
import { LigneLivClientRoutingModule } from './route/ligne-liv-client-routing.module';

@NgModule({
  imports: [SharedModule, LigneLivClientRoutingModule],
  declarations: [
    LigneLivClientComponent,
    LigneLivClientDetailComponent,
    LigneLivClientUpdateComponent,
    LigneLivClientDeleteDialogComponent,
  ],
  entryComponents: [LigneLivClientDeleteDialogComponent],
})
export class LigneLivClientModule {}
