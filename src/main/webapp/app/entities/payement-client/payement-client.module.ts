import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PayementClientComponent } from './list/payement-client.component';
import { PayementClientDetailComponent } from './detail/payement-client-detail.component';
import { PayementClientUpdateComponent } from './update/payement-client-update.component';
import { PayementClientDeleteDialogComponent } from './delete/payement-client-delete-dialog.component';
import { PayementClientRoutingModule } from './route/payement-client-routing.module';

@NgModule({
  imports: [SharedModule, PayementClientRoutingModule],
  declarations: [
    PayementClientComponent,
    PayementClientDetailComponent,
    PayementClientUpdateComponent,
    PayementClientDeleteDialogComponent,
  ],
  entryComponents: [PayementClientDeleteDialogComponent],
})
export class PayementClientModule {}
