import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LigneCmdClientComponent } from './list/ligne-cmd-client.component';
import { LigneCmdClientDetailComponent } from './detail/ligne-cmd-client-detail.component';
import { LigneCmdClientUpdateComponent } from './update/ligne-cmd-client-update.component';
import { LigneCmdClientDeleteDialogComponent } from './delete/ligne-cmd-client-delete-dialog.component';
import { LigneCmdClientRoutingModule } from './route/ligne-cmd-client-routing.module';

@NgModule({
  imports: [SharedModule, LigneCmdClientRoutingModule],
  declarations: [
    LigneCmdClientComponent,
    LigneCmdClientDetailComponent,
    LigneCmdClientUpdateComponent,
    LigneCmdClientDeleteDialogComponent,
  ],
  entryComponents: [LigneCmdClientDeleteDialogComponent],
})
export class LigneCmdClientModule {}
