import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CartsComponent } from './list/carts.component';
import { CartsDetailComponent } from './detail/carts-detail.component';
import { CartsUpdateComponent } from './update/carts-update.component';
import { CartsDeleteDialogComponent } from './delete/carts-delete-dialog.component';
import { CartsRoutingModule } from './route/carts-routing.module';

@NgModule({
  imports: [SharedModule, CartsRoutingModule],
  declarations: [CartsComponent, CartsDetailComponent, CartsUpdateComponent, CartsDeleteDialogComponent],
  entryComponents: [CartsDeleteDialogComponent],
})
export class CartsModule {}
