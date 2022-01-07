import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UniteArticleComponent } from './list/unite-article.component';
import { UniteArticleDetailComponent } from './detail/unite-article-detail.component';
import { UniteArticleUpdateComponent } from './update/unite-article-update.component';
import { UniteArticleDeleteDialogComponent } from './delete/unite-article-delete-dialog.component';
import { UniteArticleRoutingModule } from './route/unite-article-routing.module';

@NgModule({
  imports: [SharedModule, UniteArticleRoutingModule],
  declarations: [UniteArticleComponent, UniteArticleDetailComponent, UniteArticleUpdateComponent, UniteArticleDeleteDialogComponent],
  entryComponents: [UniteArticleDeleteDialogComponent],
})
export class UniteArticleModule {}
